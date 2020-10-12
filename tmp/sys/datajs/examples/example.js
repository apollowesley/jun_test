  /**
   * 赋值
   */
  function setValue() {
    try {
      var expression = document.getElementById('setterExpression').value;
      eval(expression);
    } catch (e) {
      alert(e.message);
    }
  }

  /**
   * 取值
   */
  function getValue() {
    try {
      var expression = document.getElementById('getterExpression').value;
      var value = eval(expression);
      alert(JSON.stringify(value, null, 2));
    } catch (e) {
      alert(e.message);
    }
  }

  /**
   * 设置表达式
   */
  function setExpression(id, expression) {
    document.getElementById(id).value = expression;
  }