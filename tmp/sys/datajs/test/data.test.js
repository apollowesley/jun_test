
//
// value
//
QUnit.test('value', function(assert) {
  // 获取数据
  assert.strictEqual(jQuery('#single').prop('checked'), false);
  assert.strictEqual($$data.value(document, 'single'), false);

  // 设置数据
  $$data.value(document, 'single', true);
  assert.strictEqual(jQuery('#single').prop('checked'), true);
  
  // 表达式只有一个字符且不是 "*" 号
  var jqNode = jQuery('<div></div>');
  jqNode.append('<input data-name="a" value="A" />');
  jqNode.append('<input data-name="b" value="B" />');
  assert.strictEqual($$data.value(jqNode[0], 'a'), 'A');
});

//
// getValues
//
QUnit.test('getValues', function(assert) {
  jQuery('#single').prop('checked', true);
  jQuery('#hobby').val(['football']);

  var elements = [
    jQuery('#single')[0],
    jQuery('#hobby')[0]
  ];

  var expectedObj = {
    single: true,
    hobby: ['football']
  };

  assert.deepEqual($$data.getValues(elements), expectedObj);
});

//
// setValues
//
QUnit.test('setValues', function(assert) {
  assert.strictEqual(jQuery('#single').prop('checked'), false);
  assert.strictEqual(jQuery('#hobby').val(), null);

  var elements = [
    jQuery('#single')[0],
    jQuery('#hobby')[0]
  ];

  $$data.setValues(elements, {
    single: true,
    hobby: ['football']
  });

  assert.strictEqual(jQuery('#single').prop('checked'), true);
  assert.deepEqual(jQuery('#hobby').val(), ['football']);
});

//
// getValue
//
QUnit.test('getValue', function(assert) {
  // 单选下拉框
  {
    var element = jQuery('#sex')[0];
    assert.strictEqual($$data.getValue(element), '');

    jQuery('#sex').val('female');
    assert.strictEqual($$data.getValue(element), 'female');
  }

  // 多选下拉框
  {
    var element = jQuery('#hobby')[0];
    assert.deepEqual($$data.getValue(element), []);

    jQuery('#hobby').val(['football', 'tennisball']);
    assert.deepEqual($$data.getValue(element), ['football', 'tennisball']);
  }

  // 文本框
  {
    var element = jQuery('#description')[0];
    assert.strictEqual($$data.getValue(element), '');

    jQuery('#description').val('Hello, world.');
    assert.strictEqual($$data.getValue(element), 'Hello, world.');
  }

  // 复选框
  {
    var element = jQuery('#single')[0];
    assert.strictEqual($$data.getValue(element), false);

    jQuery('#single').prop('checked', true);
    assert.strictEqual($$data.getValue(element), true);
  }
});

//
// setValue
//
QUnit.test('setValue', function(assert) {
  // 单选下拉框
  {
    assert.strictEqual(jQuery('#sex').val(), '');

    $$data.setValue(jQuery('#sex')[0], 'female');
    assert.strictEqual(jQuery('#sex').val(), 'female');
  }

  // 多选下拉框
  {
    assert.deepEqual(jQuery('#hobby').val(), null);

    $$data.setValue(jQuery('#hobby')[0], ['football', 'tennisball']);
    assert.deepEqual(jQuery('#hobby').val(), ['football', 'tennisball']);
  }

  // 文本框
  {
    assert.strictEqual(jQuery('#description').val(), '');

    $$data.setValue(jQuery('#description')[0], 'Hello, world.');
    assert.strictEqual(jQuery('#description').val(), 'Hello, world.');
  }

  // 复选框
  {
    assert.strictEqual(jQuery('#single').prop('checked'), false);

    $$data.setValue(jQuery('#single')[0], true);
    assert.strictEqual(jQuery('#single').prop('checked'), true);
  }
});

//
// queryName
//
QUnit.test('queryName', function(assert) {
  {
    var element = $$data.queryName(document, 'user.name');
    assert.strictEqual(element, jQuery('#userName')[0]);
  }

  {
    var element = $$data.queryName(document, 'user.age');
    assert.strictEqual(element, jQuery('#userAge')[0]);
  }
});

//
// queryNameAll
//
QUnit.test('queryNameAll', function(assert) {
  var elements = $$data.queryNameAll(document, 'user.*');

  var expectedObj = [
    jQuery('#userName')[0],
    jQuery('#userAge')[0]
  ];

  assert.strictEqual(elements[0], expectedObj[0]);
  assert.strictEqual(elements[1], expectedObj[1]);
});

//
// parseName
//
QUnit.test('parseName', function(assert) {
  // "a"              => "[data-name="a"]"
  assert.strictEqual($$data.parseName('a'), '[data-name="a"]');

  // "*"              => "[data-name]"
  assert.strictEqual($$data.parseName('a*'), '[data-name^="a"]');

  // "*"          => "[data-name]"
  assert.strictEqual($$data.parseName('*'), '[data-name]');

  // ["a", "a*", "*"] => "[data-name="a"],[data-name^="a"],[data-name]"
  assert.strictEqual($$data.parseName(['a', 'a*', '*']), '[data-name="a"],[data-name^="a"],[data-name]');
});

//
// isPlainObject
//
QUnit.test('isPlainObject', function(assert) {
  // 这些是纯粹对象
  assert.ok($$data.isPlainObject({}));
  assert.ok($$data.isPlainObject({'key': 'value'}));

  // 日期、数组、数字、字符串等不是纯粹对象
  assert.notOk($$data.isPlainObject(new Date()));
  assert.notOk($$data.isPlainObject([]));
  assert.notOk($$data.isPlainObject(1));
  assert.notOk($$data.isPlainObject('A'));
});

//
// prefix
//
QUnit.test('prefix', function(assert) {
  var obj = {
    name: 'xiaoming', 
    age: 11
  };

  var newObj = $$data.prefix(obj, 'user.');

  var expectedObj = {
    'user.name': 'xiaoming',
    'user.age': 11
  };

  assert.deepEqual(newObj, expectedObj);
});

//
// unprefix
//
QUnit.test('unprefix', function(assert) {
  var obj = {
    'user.name': 'xiaoming',
    'user.age': 11
  };

  var newObj = $$data.unprefix(obj, 'user.');

  var expectedObj = {
    name: 'xiaoming',
    age: 11
  };

  assert.deepEqual(newObj, expectedObj);
});

