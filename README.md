# QQCleaner(Xposed Module)
如你所见 这是一个低技术力的xposed模块 如果有hxd觉得这玩意好使的话 帮我点个小星星吧

有bug的话欢迎提交pr和issue(由于本人技术力过于低下的原因可能不会修复

(仅支持8.2.8+的QQ)
本模块不会删除消息记录以及QQ接收的文件 只会清理堆积在那里的图片、视频、广告等缓存

注:本模块完全免费且开源，一切开发旨在学习，请勿用于非法用途。

![pic](https://i.loli.net/2020/12/03/7Jnxv4ORbpNhQiV.jpg)
## 使用方法
1.勾上模块

2.重启QQ

3.设置->关于->QQ瘦身

### 瘦身内容如下
一键瘦身:

cache | diskcache | ScribbleCache | photo | shortVideo | thumb | qbosssplahAD | pddata | chatpic | QQEditPic | mini | msflogs | .apollo

彻底瘦身(包括一键瘦身):

.font_info | .hiboom_font | .gift | .pendant | .profilecard | .sticker_recommended_pics | .troop/enter_effects | .vaspoke | newpoke | poke | .vipicon | DoutuRes | funcall | head | hotpic | pe | qav | qqmusic | QQfile_recv/thumbnails | QQfile_recv/tmp | QQfile_recv/trooptmp

具体目录请看工具类CleanManager.kt

### TODO List
[√]1.自定义瘦身

[√]2.定时瘦身

[√]3.有个好康一点的UI

[?]4.添加微信瘦身

[]5.适配TIM

[]6.自定义瘦身间隔
