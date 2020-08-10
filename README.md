# QQCleaner
(仅支持8.2.8+的QQ)
本模块不会删除消息记录以及QQ接收的文件 只会清理堆积在那里的图片、视频、广告等缓存

QQ太弱智了 只要你不手动清理 他就会一直膨胀 所以整了这个模块自用

## 使用方法
1.勾上模块

2.重启QQ

3.设置->关于->一键瘦身/彻底瘦身(长按)

### 瘦身内容如下
一键瘦身:

cache | diskcache | ScribbleCache | photo | shortVideo | thumb | qbosssplahAD | pddata | chatpic | QQEditPic 

彻底瘦身:

.font_info | .gift | .pendant | .profilecard | .sticker_recommended_pics | .troop/enter_effects | .vaspoke | newpoke | poke | .vipicon | DoutuRes | funcall | head | hotpic | pe | qav | qqmusic

具体目录请看MainHook类中的cleanCache方法
