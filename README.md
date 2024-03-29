### 跨进程通信（IPC）的几种方式：
#### 1、使用Bundle：
四大组件的三大组件（Activity、Service、Receiver）都支持在Intent中传递Bundle数据，由于Bundle实现了Parcelable接口，所以它可以很方便的在不同的进程间传输。这是最简单的进程间通信方式。

#### 2、使用文件共享：
两个进程通过读/写同一个文件来交换数据，比如A进程把数据写入文件，B进程又从该文件中读取数据。文件共享方式适合在对数据同步要求不高的进程之间进行通信，且要处理并发读写的问题。

#### 3、使用Messenger（信使---串行的方式）：
通过Messenger可以在不同的进程中传递Message对象，在Message中放入我们需要传递的数据，就可以轻松的实现进程间通信了。Messenger是一种轻量级的IPC方案，它的底层实现是AIDL。

#### 4、使用AIDL（Android接口定义语言）：
AIDL底层的实现是Binder，下面会单独介绍AIDL的实现原理。

#### 5、使用ContentProvider  『 [ContentProvider基础应用](https://blog.csdn.net/u012440207/article/details/122062280?spm=1001.2014.3001.5501) 』：
ContentProvider是Android中提供的专门用于不同应用间进行数据共享的方式，ContentProvider的底层实现同样是Binder。

#### 6、使用Socket：
Socket也称为“套接字”，是网络通信中的概念，它分为流式套接字和用户数据报套接字两种，分别对应于网络传输控制层中的TCP和UDP协议。TCP协议是面向连接的协议，提供稳定的双向通信功能，TCP连接的建立需要进行“三次握手”才能完成，为了提供稳定的数据传输功能，其本身提供了超时重传机制，因此具有很高的稳定性；而UDP是无连接的，提供不稳定的单向通信功能，当然UDP也可以实现双向通信功能。在性能上，UDP具有更好的效率，其缺点是不能保证数据一定能够准确传输，尤其是在网络拥塞的情况下。
