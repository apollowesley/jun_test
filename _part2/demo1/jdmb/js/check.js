/**
 * Created by JackWong on 2016/9/23.
 */

function check() {
    var jdtit = document.getElementsByClassName("jd_shop_tit");
    for (var i = 0, len = jdtit.length; i < len; i++ ){
        var checkBox = jdtit[i].getElementsByClassName("jd_check_box")[0];
        checkBox.addEventListener("click",function () {
            var isCheck = false;
            var checked = this.getAttribute("checked");
            if (checked != null){
                this.removeAttribute("checked");
            }else {
                this.setAttribute("checked","");
                isCheck = true;
            }
            var parent = this.parentNode.parentNode.parentNode;
            var shopProducts = parent.getElementsByClassName("jd_shop_con");
            var len = shopProducts.length;
            for (var i = 0; i < len;i++){
                var checkBox = shopProducts[i].getElementsByClassName("jd_check_box")[0];
                isCheck ? checkBox.setAttribute("checked",""):checkBox.removeAttribute("checked");
            }

            // if (isCheck){
            //     /*如果为真下面的复选框都应该被选中*/
            //
            // }else {
            //     /*如果为假下面的复选框都要取消掉*/
            //
            // }
            sumMoney();


        });

    }
    productCheck();
}

/**/
function productCheck() {
    var checkProducts = document.getElementsByClassName("jd_shop_con");

    for (var i = 0; i < checkProducts.length; i++){
        var checkBoxs = checkProducts[i].getElementsByClassName("jd_check_box");
        for (var m = 0; m < checkBoxs.length;m++){
            checkBoxs[m].addEventListener("click",function () {
                var checked = this.getAttribute("checked");
                if (checked != null){
                    this.removeAttribute("checked");

                }else {
                    this.setAttribute("checked","");
                }
                sumMoney();
            });
        }
    }

}

/*计算总额*/
function  sumMoney() {
    var sum = 0;
    var checkProducts = document.getElementsByClassName("jd_shop_con");
    for (var i = 0; i < checkProducts.length; i++){
        var checkBoxs = checkProducts[i].getElementsByClassName("jd_check_box");
        for (var m = 0; m < checkBoxs.length;m++){

                var checked = checkBoxs[m].getAttribute("checked");
                if (checked != null){
                   var priceParent = checkBoxs[m].parentNode;
                    var price = priceParent.nextElementSibling.getElementsByClassName("price")[0];
                    sum += parseFloat(price.innerHTML.substring(1));

                }
        }
    }
    document.getElementsByClassName("sum")[0].innerHTML = " 合计:&yen;"+sum.toFixed(2);
}