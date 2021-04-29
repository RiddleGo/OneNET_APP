# OneNET_APP

##这一个APP，功能实现了简单的http的API调用

***背景：因为最近做一个物联网的项目，需要我把终端采集的数据进行可视化，同时需要随时随地的检测，于是搭建了这个简单的项目，没有很精致，只是实现了功能。***

**1. 工作流程**
```flow
st=>start: Start
e=>end: End
op1=>operation: 高精度电路采集数据
op2=>operation: WSN实现数据局域传输到汇聚节点
op3=>operation: 数据通过IPv4传输到OneNet云平台
op4=>operation: 云平台存储数据并进行Web页面可视化
op5=>operation: 手机App通过调用Api进行移动端数据监测

cond1=>condition: 01
cond2=>condition: 02
cond3=>condition: 03
cond4=>condition: 04
cond5=>condition: 05

io1=>inputoutput: 数据传输
io2=>inputoutput: 002
io3=>inputoutput: 003

st->op1(right)->io1->op2

op2->op3->op4->op5->e
```
**2. 涉及技术**
  1. 模拟电路涉及
  2. Zigbee
  3. TCP/IP
  4. Java
  5. Js
  6. Android

**3. 大概简述**
这是个完成度很完整，但是细节还很匮乏的项目，我是一个光学的小白换到现在的方向，完成这个项目学到了很多的东西。无论是从基础的电路设计，还是到研读TCP/IP进行封装数据帧，再到设计了一个手机的APP。原本是计划结合IPv6,所以OneNet上面需要用到新的Mqtt那个工作台，而那个工作台是不支持命令下发的，所以我就用了IPv4，这样当在通过下下发命令的时候，终端就开始工作，当不需要的时候就会处于睡眠的情况，这样可以大大降低功耗。

**4. 手机APP**
OneNet给了一个官方的SDK，但是这个SDK包含了太多的东西，对我这种Java和安卓小白一点也不友好。于是乎就自己根据官方技术文档，没有用他们的，自己照猫画虎写了一个App,基本上能满足功能。

```flow
st=>start: Start
e=>end: End
op1=>operation: 登录
op2=>operation: Web折线图显示
op3=>operation: node节点实时数据显示
op4=>operation: 云平台存储数据并进行Web页面可视化
op5=>operation: 手机App通过调用Api进行移动端数据监测

cond1=>condition: Web界面显示选择
cond2=>condition: 02
cond3=>condition: 03
cond4=>condition: 04
cond5=>condition: 05

io1=>inputoutput: 数据传输
io2=>inputoutput: 002
io3=>inputoutput: 003

st->op1->cond1
cond1(yes)->op2
cond1(no)->op3
op2->e
op3->e
```

**5. 希望能做出越来越好的开源项目**