function add(arg1, arg2) {
  return arg1 + arg2;
}

function concat(arg1, arg2) {
  return arg1 + arg2;
}

function array(arg1, arg2, arg3) {
  return new Array(arg1, arg2, arg3);
}

console.log(add(1, 2));

console.log(concat(1, "2"));

var list = array(1, "2", "3");
list.map(element-- > element + "_test");
console.log(list);
