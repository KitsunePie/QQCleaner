import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo

@KotlinPoetKspPreview
class ThemeProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbol =
            resolver.getSymbolsWithAnnotation("me.kyuubiran.qqcleaner.annotation.ThemeColor")
                .filter { it is KSClassDeclaration }
                .find { (it as KSClassDeclaration).simpleName.asString() == "QQCleanerColors" }
        // only process QQCleanerColors
        if (symbol == null) {
            logger.warn(">>>> ThemeProcessor skipped because empty <<<<")
            return emptyList()
        }

        val primaryConstructor = (symbol as KSClassDeclaration).primaryConstructor
        if (primaryConstructor == null) {
            logger.warn(">>>> ThemeProcessor skipped because not have primaryConstructor <<<<")
            return emptyList()
        }
        val args = primaryConstructor.parameters

        logger.warn(">>>> ThemeProcessor Processing <<<<")

        val animateColorAsState = ClassName("androidx.compose.animation", "animateColorAsState")
        val composableClass = ClassName("androidx.compose.runtime", "Composable")
        val compositionLocalProvider = ClassName("androidx.compose.runtime", "CompositionLocalProvider")
        val compositionLocalOf = ClassName("androidx.compose.runtime", "compositionLocalOf")
        val darkClass = ClassName("me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme", "Dark")
        val darkColorPalette = ClassName("me.kyuubiran.qqcleaner.ui.theme.colors", "DarkColorPalette")
        val lightClass = ClassName("me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme", "Light")
        val lightColorPalette = ClassName("me.kyuubiran.qqcleaner.ui.theme.colors", "LightColorPalette")
        val materialTheme = ClassName("androidx.compose.material", "MaterialTheme")
        val themeClass = ClassName("me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme", "Theme")
        val tweenSpec = ClassName("androidx.compose.animation.core", "TweenSpec")
        val providableCompositionLocal = ClassName("androidx.compose.runtime", "ProvidableCompositionLocal")
        val qqCleanerColors = ClassName("me.kyuubiran.qqcleaner.ui.theme.colors", "QQCleanerColors")
        val shapes = ClassName("androidx.compose.material.MaterialTheme", "shapes")

        val composable = AnnotationSpec.builder(composableClass).build()
        val composableUnit = LambdaTypeName.get(returnType = Unit::class.asTypeName())
            .copy(annotations = arrayListOf(composable))

        // private val LocalQQCleanerColors: ProvidableCompositionLocal<QQCleanerColors>
        val typeSpec = providableCompositionLocal.parameterizedBy(qqCleanerColors)
        val localQQCleanerColors = PropertySpec.builder("LocalQQCleanerColors", typeSpec)
            .addModifiers(KModifier.PRIVATE)
            .initializer("%T{\n\t%T\n}", compositionLocalOf, lightColorPalette)
            .build()



        val themeMethod = FunSpec.builder("QQCleanerTheme").run {
            addAnnotation(composable)
            val colorTheme = ParameterSpec.builder("colorTheme", themeClass)
                .defaultValue("%T", lightClass)
                .build()
            val content = ParameterSpec.builder("content", composableUnit).build()
            addParameter(colorTheme)
            addParameter(content)

            // gen targetColors
            addCode(CodeBlock.Builder().run {
                add("val targetColors = when (colorTheme) {\n")
                add("\t%T -> %T\n", lightClass, lightColorPalette)
                add("\t%T -> %T\n", darkClass, darkColorPalette)
                add("}\n\n")
                build()
            })

            addStatement("// region Colors")
            args.forEach {
                val fieldName = it.name!!.asString()
                addCode(CodeBlock.Builder().run {
                    add("val $fieldName = %T(\n", animateColorAsState)
                    add("\ttargetColors.$fieldName,\n")
                    add("\t%T(600)\n", tweenSpec)
                    add(")\n")
                    build()
                })
            }
            addStatement("// endregion\n")

            addCode(CodeBlock.Builder().run {
                add("val colors = %T(\n", qqCleanerColors)
                args.forEach {
                    val fieldName = it.name!!.asString()
                    add("\t$fieldName = $fieldName.value,\n")
                }
                add(")\n\n")
                build()
            })

            addCode(CodeBlock.Builder().run {
                add("%T(LocalQQCleanerColors provides colors) {\n", compositionLocalProvider)
                add("\t%T(\n", materialTheme)
                add("\t\tshapes = %T,\n", shapes)
                add("\t\tcontent = content\n")
                add("\t)")
                add("\n}")
                build()
            })

            build()
        }

        FileSpec.builder("me.kyuubiran.qqcleaner.ui.theme", "QQCleanerTheme")
            .addComment("这个文件由ksp自动生成，直接修改是无效的。")
            .addProperty(localQQCleanerColors)
            .addFunction(themeMethod)
            .indent("    ")
            .build()
            .writeTo(
                codeGenerator,
                Dependencies(true, symbol.containingFile!!)
            )
        return emptyList()
    }
}

@KotlinPoetKspPreview
class ThemeProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return ThemeProcessor(environment.codeGenerator, environment.logger)
    }
}
