

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper
import sun.nio.cs.ext.DoubleByteEncoder

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

  /**22
   * List
   */
  import scala.collection.immutable.List
  val list = List[Any](2,"scala")
  val list22 = Nil   //空集合
  println(list(1)) //打印第二个元素
  //元素追加
  val list3 = list22 :+ 4
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
  var result = map.get("frank").get
  //使用getOrElse取值
  map2.getOrElse("frank",3)  //第一个参数为key   第二个参数为默认参数

  val map4 =mutable.Map("scala" -> 2,"python" -> 3)
  //增加单个元素
  map4 += ("java" -> 5)
  println(map4)
  //增加多个元素      如果key存在就是更新   不存在就是添加
  //val map5 = map2 + map3
  //println(map5)
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

  /**
   * 集合化简
   * reduceLeft从左往右依次用左右函数元素
   * 将返回结果与第三个元素继续作为函数参数 循环执行
   * reduceRight与此相反
   */
  val list9 = List(1,2,3,4,5,9)
  val result9 = list9.reduceRight(_ + _)
  list9.reduceRight(min5)

  def min5(n1: Int,n2: Int): Int = {
    if (n1 > n2) n2 else n1
  }

  /**
   * 折叠和化简几乎相同
   */
  val list8 = List(1,2,3,4,5,9)
  val result8 = list8.foldRight(5)(_ + _)
  list9.foldRight(5)(min)

  def min(n1: Int,n2: Int): Int = {
    if (n1 > n2) n2 else n1
  }
  //等价于    把5作为第一个元素  执行reduceLeft
  /**
   * 折叠的简写
   */
  val list11 = (1 /: list8)(min2)  //等价于 1左折叠
  def min2(n1: Int,n2: Int): Int = {
    if (n1 > n2) n2 else n1
  }
  val list13 = List(2,5,7)
  val list12 = (list13 :\ 8)(min3)  //注意list方向   list在哪边：在哪边
  def min3(n1: Int,n2: Int): Int = {
    if (n1 > n2) n2 else n1
  }

  /**
   * 扫描：
   * 即对集合中的每一个元素进行fold操作对产生的所有中间结果重新放入一个集合中
   */
  val i9 = (1 to 5).scanRight(5)(sum)  //结果为20,19,1714,10,5
  def sum(n1: Int,n2: Int): Int = {
    n1 + n2
  }

  /**
   * 案例：使用fold来进行序列字母统计
   */
  val sentence = "ABBBBBCCCKJKK"
  //创建一个参数作为左折叠的第一个参数
  val countMap = mutable.Map[Char,Int]()
  sentence.foldLeft(countMap)(charCount)
  def charCount(map: mutable.Map[Char,Int],char: Char): mutable.Map[Char,Int] = {
    map += (char -> (map.getOrElse(char,0) + 1))
  }

  /**
   * 拉链
   * 1，2，3
   * 4，5，6
   * （1，4），（2，5），（3，6）
   */
  val listx = List(1,2,3)
  val listy = List(4,5,6)
  val listm = listx.zip(listy)   //合并后每一个元素都是对偶元组
  //遍历
  for(item <- listm) {
    println("x:"+item._1 + "||" + "y:"+item._2)
  }
  /**
   * 迭代器
   *
   */
  val iterator = List(2,6,8).iterator
  while (iterator.hasNext) {
    println(iterator.next())
  }

  for(enum <- iterator) {
    println(enum)
  }

  /**
   * Stream 流
   */
  def numsForm(n: BigInt) :Stream[BigInt] = n #:: numsForm(n * 2)
  val stream = numsForm(2)

  /**
   * Stream[BigInt]指定流数据类型
   *  def numsForm用函数指定数据生成规则  第一个元素是 n 规则为n * 2
   */

  /**
   * View  实现普通集合lazy懒加载
   */
  def eq(i: Int): Boolean = {
    i.toString.equals(i.toString.reverse)
  }
  val listT = List(2,3,5,88,99).view.filter(eq)

  /**
   * 并行集合
   */
  (1 to 20).par.foreach(println(_))
  val listM = List(2,4,6)
  listM.par.foreach(
    println(_)  //输出的结果是无序的说明任务是分配给多个cpu执行的
  )

  /**
   *
   * 模式匹配  和Java中的switch case类似
   * 模式匹配会将匹配到的代码块中的最后一句作为返回值返回
   */
  val oper = '#'
  val n1 = 10
  val n2 = 11
  val result2 = 0

  oper match {
    case '+' => {result = n1 + n2}
    case '-' => result = n1 - n2
    case _ => println("error")
  }
  println(result)
  /**
   * 模式匹配条件守卫
   */
  val str = "asdsadas"
  for(s <- str) {
    s match {
      case 'a' => {
        if(s > 0) {//条件守卫
        println(s)
    }
    }
      case 'b' => {
        println(s)
    }
      case _ => println("error")
    }
  }
  /**
   * 模式中的变量
   */
  val str2 = "asdsadas"
  for(s <- str2) {
    s match {
      case 'a' => {
        if(s > 0) {//条件守卫
          println(s)
        }
      }
      case 'b' => {
        println(s)
      }
      case myChar =>{
        //会将s直接赋值给myChar     无条件的匹配
        println(myChar)
      }
      case _ => println("error")
    }
  }
  /**
   * 模式匹配之类型匹配
   */
  val a = 5
  val obj =  if(a == 1) List(2,4)
  else if(a ==2) Map(("frank",1),("luck",2))
  else Array("aa",2)
  //类型匹配  只有在类型匹配的时候才会赋值然后执行代码块
  obj match {
    case a: Map[String,Int] => println(a)
    case b: Array[Any] => println(b)
  }

  /**
   * 匹配数组
   */
  val array0 = Array(Array(0,1),Array(1,8),Array(1,3,5))
  for (array <- array0) {
    array match {
      case Array(x,y) => println(x,y)   //匹配数组中两个不同的元素
      case Array(0,1) => println("")
      case Array(0,_*) => println("")  //以0为开头的数组
      case  _ => println("error")
    }
  }
  /**
   * 匹配列表
   */
  for (list <- Array(List(1, 0), List(1, 0, 2), List(4, 0))) {
    list match {
      case 0 :: Nil => println() //以0开始的列表
      case x :: y :: Nil => println()  //x y占位的列表
      case 0 :: tail => println()
      case  _ => println()
    }
  }
  /**
   * 匹配元组
   */
  for (pa <- Array((1, 0), (0, 2),(4, 0))) {
    pa match {
      case (0,x) => println() //以0开始的元组
      case (x,y) => println()  //x y占位的元组
      case () => println()
      case  _ => println()
    }
  }
  /*
  Object Square {
    //unapply是对象提取器
    //接收Double类型  返回Option类型  返回具体值是math.sqrt(z)并放入Some集合中
    def unapply(z: Double): Option[Double] = Some(math.sqrt(z))
    def apply(z: Double): Double = z*z
  }
  val number: Double = 36.  0
  //match 到case Square之后 调用 unapply（z:Double）然后z的值就是
  number match {
    case Square(n) => println(n)
    case _ => println("nothing matched")
  }


*/
/*
  Object Names {
    //当构造器为多个参数是就会触发此方法   unapply
    def unapply(str: String): Option[Seq[String]] = {
        if(str.contains(",")) Some(str.split(","))
        else None
    }
  }*/
  /**
   * 变量声明中模式使用
   */
  var (x, y, z) = (1, 2, "hello")
  val (q,r) = BigInt(10) /% 2   // q=BigInt(10)/2  r=BigInt(10)%2
  val arr = Array(1,2,4,6)
  val Array(first,second,_) =arr
  println(first,second)

  /**
   * for循环中的模式
   */
  val map11 = Map("frank"->2,"tom"->3)
  for ((x,y) <- map11) {
    println(x,y)
  }
  for ((x,0) <- map11) {
    println(x,0)
  }
  for ((x,y) <- map11 if y == 0) {
    println(x,0)
  }
  for (("tom",y) <- map11) {
    println("tom",y)
  }

  /**
   * 样例类 为模式匹配而优化的类  为了模式匹配的简洁性
   */
  abstract class Amount
  case class Dollar(value: Double) extends Amount
  case class Currency(value: Double,unit: String) extends Amount
  case object NoAmount extends Amount //样例类

  for(amt <- Array(Dollar(200.00),Currency(200.00,"frnak"),NoAmount)) {
    amt match {
      case Dollar(v) => println(v)
      case Currency(x,y) => println(x,y)
      case NoAmount => println("NoAmount")
    }
  }
  /**
   * 样例类的copy方法   复制一个一模一样类 参数默认可省略
   */
  val amt2 = Currency(4.00,"frank")
  val amt3 = amt2.copy() //创建和amt2属性一样的类
  val amt4 = amt2.copy(value = 3.8)   //属性值修改
  val amt5 = amt2.copy(unit = "jack")


  /**
   * 批配嵌套结构
   */
  abstract class Item //项
  case class Book(description: String,price: Double) extends Item
  case class Bundle(description: String,discount: Double,item: Item*) extends Item

  val sale = Bundle("书籍",10,Book("漫画",40),Bundle("文学作品",20,Book("阳关",20),Book("围城",40)))

  //使用case获取到漫画
  val result5 = sale match {
    case Bundle(_,_,Book(desc,_),_,_*) => println(desc)
  }
  println(result5)
  //通过@表示法将嵌套的值绑定在变量  _*绑定剩余item到rest
  val result6 = sale match {    //_*表示所有   _表示忽略即可
    case Bundle(_,_,art @ Book(_,_), rest @_*) => println(art,rest)
  }
  // _*表示所有   _表示忽略即可
  val result7 = sale match {    //_*表示所有   _表示忽略即可
    case Bundle(_,_,art @ Book(_,_),rest) => println(art,rest)
  }

  /**
   * 案例完成
   */
  def price(item: Item): Double = {
    item match {
    case Book (_, price) => price
    case Bundle(_,disc,its @ _*) => its.map(price).sum - disc

  }
  }

  /**
   * 密封类   只能在当前文件源继承使用   其他地方无法使用
   */
  abstract sealed class Item2 //项
  case class Book2(description: String,price: Double) extends Item
  case class Bundle2(description: String,discount: Double,item: Item*) extends Item


  /**
   * 偏函数
   * Any为偏函数的输入值类型，Int为偏函数的输出值类型
   * isDefinedAt方法返回值为true则调用apply方法创建对象实例，否则直接过滤
   * apply构造器 对传入的值加1然后返回（新的集合）
   */
  val patrtialFun = new PartialFunction[Any,Int] {
    override def isDefinedAt(x: Any): Boolean = x.isInstanceOf[Int]

    override def apply(v1: Any): Int = v1.asInstanceOf[Int] + 1

  }
  /**
   * 偏函数的使用
   * 如果是偏函数 则不能使用map因该使用collect
   *
   */
  List(1,2,3,"frank").collect(patrtialFun)

  /**
   * 偏函数的简化 1
   */
  def patrtialFun2: PartialFunction[Any,Int] = {
    case x: Int => x + 1
  }
  /**
   * 偏函数的简化 2
   */
  List(1,2,3,"frank").collect{ case x: Int => x + 1}

  /**
   * 作为参数的函数
   * 在scala中函数也是有类型的
   * 一个参数的函数类型为function1
   * 二个参数的函数类型为function2
   *
   */
  def tranlate(x: Int): Double = {
    x.toDouble
  }
  List(2,5,7).map(tranlate(_))  //_代表从集合中遍历的每一个元素

  /**
   * 匿名函数
   */
  val triple = (x: Double) => 3 * x
  //匿名函数等于形参加函数体
  //匿名函数返回值类型通过类型推导
  //如果函数体有多行则使用大括号包裹

  /**
   * 高阶函数
   * 能够接受函数作为参数的函数称为高阶函数
   */
  def sum22(n2: Double): Double = {
    n2 + n2
  }
  def test(f:Double => Double,n3: Double) = {
    f(n3)
  }
  val rest6 = test(sum22 _,3.0)


  /**
   * 函数minusxy返回匿名函数
   * 可以接收返回的函数
   */
  def minusxy(x: Int) = {
    (y: Int) => x - y
  }

  val fun01 = minusxy(2)  //fun01 = (y: Int) => 2 - y
  val fun02 = minusxy(2)(3)   //(y: Int) => 2 - y   再传入3

  /**
   * 闭包就是一个函数与它引用环境所构成的整体
   */
  def minusxy2(x: Int) = {
    (y: Int) => x - y
  }
  //minusxy2函数与外部的变量x整体构成闭包
  //通过闭包我们不用每次都将suffix传入函数
  def exmp(suffix: String) = {
    (fileName: String) => {
        if(fileName.contains(suffix)) fileName
        else fileName + suffix
    }
  }

  /**
   * 函数的柯里化 ： 多个参数的函数可以转换成一个参数的函数
   */
  def sumN(n1: Int)(n2: Int) = n1 * n2

  /**
   * 体现了比较字符串的过程被分解成两个步骤
   * checkEQ完成小写转换
   * f函数完成比较
   * @param s
   */
  implicit class Test(s: String) {
    def checkEQ(ss: String)(f:(String,String) => Boolean): Boolean ={
      f(s.toLowerCase,ss.toLowerCase)
    }
  }

  /**
   * 抽象控制
   */
  def myRunThread(f1:() => Unit ) = {
    new Thread {
      override def run(): Unit = {
        f1()
      }
    }.start()
  }
  myRunThread(() => {
    println("开始干活")
    Thread.sleep(5000)
    println("over")
  })
  /**
   * 对于没有输入也没有输出的函数可以简写
   */
  def myRunThread {
    println("开始干活")
    Thread.sleep(5000)
    println("over")
  }

  /**
   * 通过控制抽象来理解while循环底层原理
   */
  def util(condition: => Boolean)(code: => Unit): Unit = {
    if(condition) {
      code
    }
  }
  var x1 = 10
  util(x1 >0){
    //无输入输出的代码块
    x1 -= 1
    println("lalalala")
  }












}
