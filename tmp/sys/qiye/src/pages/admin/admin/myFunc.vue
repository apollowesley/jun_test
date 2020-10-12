<template>
  <div class="">
    <div
      class="container"
      style="display: flex;flex-direction: column;width: 100%;"
    >
      <div style="display: flex;flex-direction: column;padding-top: 0px;">
        <div style="display: flex; flex;flex-direction: column;padding:20px; background: #fff; margin-top:  0px; box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)">
          <div
            @click="editVisible = true"
            style="font-weight:900;"
          >
            <span>昵称: {{ userData.user_name }}</span>
            <i
              style="color:#409EFF"
              class="el-icon-edit-outline"
            />
          </div>
          <div style="font-size: 14px;margin-top: 10px;">
            分类:{{ userData.rank_name }}
          </div>
        </div>
        <el-card
          class="box-card"
          style="min-height: 500px;margin-top: 10px; "
        >
          <div
            slot="header"
            style="position: relative;"
          >
            <span>您的权限范围</span>
          </div>
          <div style="display: flex;flex-direction: row;flex-wrap: wrap;min-height: 200px;align-items: center;max-height: 67vh;overflow-y:auto">
            <div
              style="min-width: 25%;"
              v-for="(item,idx) in userData.admin_rank_func"
              :key="item.id"
            >
              <div
                disabled
                style="min-width: 90%;background: #409FFF;padding: 10px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin: 10px;color: #fff;"
                type="primary"
                plain
                size="small"
              >
                {{ idx }}.{{ item.func_name }}
              </div>
              <!-- <el-button disabled style="min-width: 90%;" type="primary" plain size="small">{{idx}}.{{item.func_name}}</el-button> -->
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog
      title="编辑"
      :visible.sync="editVisible"
      width="400px"
    >
      <el-form
        ref="formEdit"
        :model="formEdit"
        label-width="100px"
        size="medium"
      >
        <el-form-item label="名称">
          <el-input
            v-model="userData.user_name"
            disabled="disabled"
          />
        </el-form-item>
        <el-form-item label="新名称">
          <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
            <el-input
              style=""
              v-model="formEdit.user_name"
              placeholder="请输入名称"
            />
          </div>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="saveEdit"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    import Request from "../../../request/index.js"
    export default {
        name: 'AdminList',
        data() {
            return {
                tableData: [],
                editVisible: false, //编辑
                formEdit: { //编辑修改表单
                    rank_name: '',
                }
            }
        },
        created() {
            // this.getRank();
            this.getData();
        },

        computed: {
            userData: {
                get() {
                    return this.tableData;
                }
            }
        },
        methods: {

            // 向服务器发送请求获取 数据
            getData() {
                Request.get('AdminAdminRank_MyRankFunc')
                    .then((res) => {
                        
                        if (res.data.code != 200) {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        }else{
                            this.tableData = res.data.data;
                        }
                    })
            },
            // 保存编辑
            saveEdit() {
                var user_name = this.formEdit['user_name'];
                var url = 'AdminAdminUser_Myupdate';

                Request(url, {
                        data: {
                            user_name
                        }
                    })
                    .then(res => {
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '成功',
                                type: 'success'
                            });

                            this.tableData.user_name = user_name;
                        } else {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        }

                    })
                this.editVisible = false;


            }
        }
    }
</script>

<style scoped>

</style>
