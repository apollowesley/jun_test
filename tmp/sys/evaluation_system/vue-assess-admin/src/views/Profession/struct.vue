<template>
<div class="app-container">
  <div  ref="visitChart" id="visitChart" :style="{width: '150%', height: '1400px'}"></div> 
</div>  
</template>

<script>
import echarts from "echarts";
import { getAllson } from "@/api/professionIndicatorRelation";

export default {
  data() {
    return {
    };
  },
  mounted() {
    var professionUid = this.$route.query.professionUid;
    var params = new URLSearchParams();
    params.append("professionUid", professionUid);
    getAllson(params).then(response => {
      console.log(response);
      if (response.code == "success") {
        var data = [];
        data.push(response.data);
        
        // 基于准备好的dom，初始化echarts实例
        var options = {
          tooltip: {
            trigger: "item",
            triggerOn: "mousemove"
          },
          series: [
            {
              type: "tree",
              data: data,
              top: "1%",
              left: "2%",
              bottom: "1%",
              right: "20%",

              symbolSize: 7,

              label: {
                normal: {
                  position: "left",
                  verticalAlign: "middle",
                  align: "left",
                  fontSize: 16
                }
              },

              leaves: {
                label: {
                  normal: {
                    position: "right",
                    verticalAlign: "middle",
                    align: "left"
                  }
                }
              },
              initialTreeDepth: 4,
              expandAndCollapse: true,
              animationDuration: 550,
              animationDurationUpdate: 750
            }
          ]
        };

        

        let visitChart = this.$echarts.init(
          document.getElementById("visitChart")
        );
        // // 绘制图表
        visitChart.setOption(options);
      }
    });

    // visitChart.on("click", function(params) {
    //   console.log("点击了", params);
    // });
  },
  created() {}
};
</script>

<style>
.app-container {
  overflow-x: scroll;
}
</style>
