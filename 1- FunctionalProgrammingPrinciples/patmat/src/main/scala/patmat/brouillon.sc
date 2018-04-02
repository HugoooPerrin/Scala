List(1,0) ::: List(1)

List(('c',1), ('r',5)).filter(e => e._1 == 'c').head._1

List('c', List(1,0,1))

List('c')

def double(i: Int): List[Int] = List(i,i)

double(7)

List(1, 2, 3).map(i => double(i)).flatten
