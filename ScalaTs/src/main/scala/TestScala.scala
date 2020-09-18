

import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.util.control.Breaks

object TestScala {
  def main(args: Array[String]): Unit = {

    /**
     * 定义变量
     */
    var varable1: String = "HelloWorld"
    println(varable1)

    var varable2: Int = 3
    var varable3: Char = '3'
    var varable4: Double = 3.0
    var varable5: Float = 3.0f
    var isResult: Boolean = false
    var isResult2: Boolean = true

    //强类型转换
    var num: Int = 2.7.toInt

    println(s"variables $varable2  $varable3")

    // 建议，在开发中需要高精度小数时，请选择Double
    var n7 = 2.2345678912f
    var n8 = 2.2345678912

    //Scala的整型，默认为Int型，声明Long型，须后加‘l’或‘L’
    var n6 = 9223372036854775807L


    /**
     * 标准输入
     */
    var age = StdIn.readShort()
    var name = StdIn.readLine()
    var salary = StdIn.readDouble()


    /* Scala运算符本质
     在Scala中其实是没有运算符的，所有运算符都是方法。
     1）当调用对象的方法时，点.可以省略
     2）如果函数参数只有一个，或者没有参数，()可以省略*/

    // 标准的加法运算
    val i: Int = 1.+(1)

    // （1）当调用对象的方法时，.可以省略
    val j: Int = 1 + (1)

    // （2）如果函数参数只有一个，或者没有参数，()可以省略
    val k: Int = 1 + 1

    println(1.toString())
    println(1 toString())
    println(1 toString)


    var age1 = StdIn.readInt()

    val res :String = if (age1 < 18){
      "童年"
    }else if(age1>=18 && age1<30){
      "中年"
    }else{
      "老年"
    }
    println(res)

    /**
     * 循环结构
     */
    for(i <- 4 to 7) {
      //前后闭合循环
      println(i)
    }

    for(j <- 7 until 9) {
      //前闭后开
      println(j)
    }

    //循环守卫
    for(m <- 3 to 8 if i != 2) {
      println(m)
    }
    for (i2 <- 1 to 10 by 2) {
      println("i=" + i2)
    }
    //嵌套循环

    for(i3 <- 2 to 7; j3 <- 3 to 8 ) {
      println(i3,j3)
    }
    //引入变量
    for(i <- 1 to 3; j = 4 - i) {
      println("i=" + i + " j=" + j)
    }
    //当for推导式仅包含单一表达式时使用圆括号，当包含多个表达式时，一般每行一个表达式，并用花括号代替圆括号，如下
    for {
      i <- 1 to 3
      j = 4 - i
    } {
      println("i=" + i + " j=" + j)
    }
    //倒序循环
    for(i <- 1 to 10 reverse){
      println(i)
    }

    /**
     * 循环中断
     *
     */
    Breaks.breakable(
      for (elem <- 1 to 10) {
        println(elem)
        if (elem == 5) Breaks.break()
      }
    )

  }





  /**
   * 函数式编程
   *
   */
  def function0(args : String): Unit = {
    println("Hello!")
  }
  //函数嵌套

  // 函数1：无参，无返回值
  def test1(): Unit ={
    println("无参，无返回值")
  }


  // 函数2：无参，有返回值
  def test2():String={
    return "无参，有返回值"
  }


  // 函数3：有参，无返回值
  def test3(s:String):Unit={
    println(s)
  }


  // 函数4：有参，有返回值
  def test4(s:String):String={
    return s+"有参，有返回值"
  }



  // 函数5：多参，无返回值
  def test5(name:String, age:Int):Unit={
    println(s"$name, $age")
  }

  //可变参数函数
  //如果参数列表中存在多个参数，那么可变参数一般放置在最后
  def test( s : String* ): Unit = {
    println(s)
  }
  //参数默认值
  //如果参数传递了值，那么会覆盖默认值
  def test3( name : String, age : Int = 30 ): Unit = {
    println(s"$name, $age")
  }



  import scala.collection.mutable.ArrayBuffer
  val arr1 =  Array[Int](3)
  val array2 =  ArrayBuffer[String]("hj","hj","j")
  val array = ArrayBuffer[Any]()
  for (elem <- arr1) {
    println(elem)
  }
  for(index <- 0 until arr1.length) {
    println(arr1(index))
  }
  //（3.1）追加数据
  array.+=(4)
  //（3.2）向数组最后追加数据
  array.append(5,6)
  //（3.3）向指定的位置插入数据
  array.insert(0,7,8)
  println("arr01.hash=" + array.hashCode())

  val array3 = Array.ofDim[Double](2,3)

  array3(1)(2) = 88

  /**
   * Scala ArrayBuffer 转 Java ArrayList
   * implicit def bufferAsJavaList[A](b : scala.collection.mutable.Buffer[A]) : java.util.List[A] = { /* compiled code */ }
   * 引入隐式函数，通过调用Java方法进行触发
   */
  import scala.collection.JavaConversions.bufferAsJavaList
  val javaArray = new ProcessBuilder(array2)
  val arrayList = javaArray.command()


  /**
   * Java arraysList 转 Scala Buffer
   * implicit def asScalaBuffer[A](l : java.util.List[A]) : scala.collection.mutable.Buffer[A] = { /* compiled code */ }
   * 隐式函数在赋值时候触发
   *
   */
  import scala.collection.JavaConversions.asScalaBuffer
  import scala.collection.mutable

  val scalaArray: mutable.Buffer[String] = arrayList

  /**
   * 多个无关的数据类型进行的组合为元组
   * 元组中最大最多22个元素   可以进行Tuple的嵌套放入达到无限循环的效果
   */
  //为了高效的操作元组 编译器根据元素个数的不同  对应不同的元组类型
  val tuple3 = (0,1,"scala")
  //访问元组
  val ele1 = tuple3._1
  /**
   * override def productArity : scala.Int = { /* compiled code */ }
   * @scala.throws[scala.IndexOutOfBoundsException](classOf[scala.IndexOutOfBoundsException])
   * override def productElement(n : scala.Int) : scala.Any = { /* compiled code */ }
   * def _1 : T1
   * def _2 : T2
   * def _3 : T3
   * }
   */
  val ele2 = tuple3.productElement(1)
  //遍历元组
  for(item <- tuple3.productIterator) {
    /**
     * 元组的遍历需要迭代器
     */
    println(item)
  }

  /**
   * List
   */
  import scala.collection.immutable.List
  val list = List[Any](2,"scala")
  val list2 = Nil   //空集合
  println(list(1)) //打印第二个元素
  //元素追加
  val list3 = list2 :+ 4
  println(list3) //底层实现list2数据拷贝再追加元素   list2还是不变的
  val list4 = 10 +: list3
  /**
   * :: 符号说明
   * 集合运算
   */
  val list5 = 4 :: 5 :: list3 :: Nil  //集合对象一定要放在最右边 运算规则从右向左
  /**
   * ::: 将集合中每一个元素放到集合中
   */
  val list6 = 5 :: 7 :: list3 ::: Nil // :::表示将符号左边的集合数据分散开逐个放入符号右边的集合

  /**
   * ListBuffer
   */
  val lb = ListBuffer[Int](2,5,8)
  for (elem <- lb) {
    //遍历是有序的
    println(elem)
  }
  println(lb(2))
  println(lb.append(2))
  lb += 2
  val lb2 = ListBuffer[Int](2,5,6)
  lb ++= lb2   //将lb2打散放入lb
  val lb3 = lb ++ lb2
  val lb4 = lb2 :+ 4
  lb.remove(2)

  /**
   * 队列Queue
   */
  import scala.collection.mutable.Queue
  val queue = new Queue[Int]
  queue ++= List(2,5,6)  //集合中的元素逐个加入
  val queue2 = new Queue[Any]
  queue2 += 2
  queue2 += List(2,4,5)     //将List直接加入队列   所以Queue的通配符为Any
  val element = queue2.dequeue()
  queue2.enqueue(2,3,5)
  println(queue2.head)
  println(queue2.last) //最后一个元素
  println(queue2.tail.tail.tail)  //队尾元素 可递归


  /**
   * 可变和不可变Map 默认Map为immutable下的Map
   * 通过tuple实现 每一对key value是Tuple2
   * 不可变的Map是有序的   可变的Map是无序的
   *
   */
  val map = Map("frank" -> 2,"jack" -> 3)
  //创建可变映射
  val map2 = scala.collection.mutable.Map("frank" -> 2,"jack" -> 3)
  //对偶元组
  val map3 = collection.mutable.Map(("frank",2),("mary",3))
  /**
   * 取值 key存在  返回对应的值  不存在直接抛出异常
   */
  println(map2("frank"))
  //所以先判断key是否存在
  if(map2.contains("frank")) {
    val element = map2("frank")
  } else {
    println("数据不存在")
  }
  //使用map.get(key).key获取
  //如果存在直接返回结果 ap.get(key)返回SOME再get取值 否则返回None
  val result = map.get("frank").get
  //使用getOrElse取值
  map2.getOrElse("frank",3)  //第一个参数为key   第二个参数为默认参数

  val map4 =mutable.Map("scala" -> 2,"python" -> 3)
  //增加单个元素
  map4 += ("java" -> 5)
  println(map4)
  //增加多个元素      如果key存在就是更新   不存在就是添加
  val map5 = map2 + map3
  println(map5)
  val map6 = map3 + ("c" -> 2)
  println(map6)
  map3 += ("golang" -> 6)
  //删除
  map3 -=("golang","java")
  map3.remove("golang")
  //map的遍历
  for((k,v) <- map) {
    println(k,v)
  }
  for(v <- map.keys) {
    println(v)
  }
  for(v <- map.values) {
    println(v)
  }
  for(v <- map) {
    //取出的值类型为Tuple2
    println(v+"key:"+v._1+"value"+v._2)
  }
  /**
   * Set集合
   */
  //不可变
  val set1 = Set(2,3,"scala")
  //可变
  val set2 = mutable.Set(2,4)
  /**
   * 高阶函数
   */
  def function0 (f: Double => Double,n1: Double) = {
    //函数function0第一个参数为函数f传入Double类型返回Double类型
    f(n1)
  }

  /**
   * map函数映射
   * 将list中的元素逐个遍历然后通过multiple函数返回Int类型的数据
   * 再将返回的数据再逐个加入到集合中去
   */
  val list7 = List(2,4,5)
  val list2 = list7.map(multiple)

  def multiple(n: Int): Int = {
    println("multiple被调用")
    2*n
  }

  /**
   * flatmap扁平化映射
   * 将list集合中所有子元素进行扁平化操作  意思为子元素如果为集合将继续不断的拆分
   */
  val names = List("frank","zachary","mary")
  println(names.flatMap(uper))
  //每个字符都算Char集合
  def uper(s: String ): String = {
    s.toUpperCase
  }

  /**
   * 过滤器
   */
  val names2 = List("frank","mary","lisa")
  val names3 = names.filter(startA) //names2还是不变的    结果用names3来接收
  //过滤器参数为一个接受String类型返回Boolean类型的函数
  def startA(s: String): Boolean = {
    s.startsWith("A")
  }





}
