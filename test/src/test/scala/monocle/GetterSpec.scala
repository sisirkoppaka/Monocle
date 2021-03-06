package monocle

import org.specs2.scalaz.Spec

import scalaz._


class GetterSpec extends Spec {

  case class Bar(i: Int)
  case class Foo(bar: Bar)

  val _bar = Getter[Foo, Bar](_.bar)
  val _i   = Getter[Bar, Int](_.i)


  // test implicit resolution of type classes

  "Getter has a Compose instance" in {
    Compose[Getter].compose(_i, _bar).get(Foo(Bar(3))) ==== 3
  }

  "Getter has a Category instance" in {
    Category[Getter].id[Int].get(3) ==== 3
  }

  "Getter has a Choice instance" in {
    Choice[Getter].choice(_i, Choice[Getter].id[Int]).get(-\/(Bar(3))) ==== 3
  }

  "Getter has a Split instance" in {
    Split[Getter].split(_i, _bar).get((Bar(3), Foo(Bar(3)))) ==== ((3, Bar(3)))
  }

  "Getter has a Profunctor instance" in {
    Profunctor[Getter].mapsnd(_bar)(_.i).get(Foo(Bar(3))) ==== 3
  }

  "Getter has a Arrow instance" in {
    Arrow[Getter].arr((_: Int) * 2).get(4) ==== 8
  }

}
