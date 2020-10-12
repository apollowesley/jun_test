<template>
  <div class="app-container" id="loadingBody">
    <div class="filter-container" style="margin: 10px 0 10px 0;">
      <el-select
        v-model="keyword"
        clearable
        size="small"     
        filterable
        remote              
        reserve-keyword
        placeholder="请输入专业名称"
        :remote-method="remoteMethod2"
        :loading="loading">
        <el-option
          v-for="item in professionOptions"
          :key="item.uid"
          :label="item.name"
          :value="item.uid">
        </el-option>
      </el-select> 
	    <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFind">查找</el-button>
      <el-button class="filter-item" type="primary" @click="handleAdd" icon="el-icon-edit">添加</el-button>      
    </div>
    <el-table :data="tableData"  style="width: 100%">
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="序号" width="60">
	      <template slot-scope="scope">
	        <span >{{scope.$index + 1}}</span>
	      </template>
	    </el-table-column>
      
      <el-table-column label="父指标名称" width="250">
	      <template slot-scope="scope">
	        <span v-if="scope.row.parentIndicator">{{ scope.row.parentIndicator.name }}</span>
	      </template>
	    </el-table-column>

	    <el-table-column label="指标名称" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.indicator.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="专业名称" width="100">
	      <template slot-scope="scope">
	        <span>{{ scope.row.profession.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="评分比例" width="100">
	      <template slot-scope="scope">
	        <span>{{ scope.row.indicatorRatio }}</span>
	      </template>
	    </el-table-column>
	    
      <el-table-column label="分数" width="100">
	      <template slot-scope="scope">
	        <span>{{ scope.row.grade }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="类型" width="100">
	   	  <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.indicator.typeStatus == 0 ">指标</el-tag>          
          <el-tag type="warning" v-if="scope.row.indicator.typeStatus == 1 ">观测点</el-tag>          
	   	  </template>
	    </el-table-column>

	    <el-table-column label="创建时间" width="180">
	      <template slot-scope="scope">
	        <span >{{ scope.row.createTime }}</span>
	      </template>
	    </el-table-column>
	    
	   	<el-table-column label="状态" width="100">
	   	  <template slot-scope="scope">
		   	  <template v-if="scope.row.status == 0">
		        <span>正常</span>
		      </template>
		      <template v-if="scope.row.status == 1">
		        <span>已删除</span>
		      </template>
	   	  </template>
	    </el-table-column>
	    
	    <el-table-column label="操作" fixed="right" min-width="150"> 
	      <template slot-scope="scope" >
	      	<el-button @click="handleEdit(scope.row)" type="primary" size="small">编辑</el-button>
	        <el-button @click="handleDelete(scope.row)" type="danger" size="small">删除</el-button>
	      </template>
	    </el-table-column>     	    
	  </el-table>

    <!--分页-->
    <div class="block">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
    </div>

    <!-- 添加或修改对话框 -->
		<el-dialog :title="title" :visible.sync="dialogFormVisible">
		  <el-form :model="form">		  	
        <el-form-item label="父指标名称" :label-width="formLabelWidth">
		      	<el-select
              v-model="form.parentUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入指标名称"
              :remote-method="remoteMethod"
              :loading="loading">
              <el-option
                v-for="item in indicatorOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
              </el-option>
            </el-select>
		    </el-form-item>

        <el-form-item label="指标名称" :label-width="formLabelWidth">
		      <el-select
              v-model="form.indicatorUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入指标名称"
              :remote-method="remoteMethod"
              :loading="loading">
              <el-option
                v-for="item in indicatorOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
              </el-option>
            </el-select>
		    </el-form-item>

        <el-form-item label="专业名称" :label-width="formLabelWidth">
		          <el-select          
              v-model="form.professionUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入专业名称"
              :remote-method="remoteMethod2"
              :loading="loading">
              <el-option
                v-for="item in professionOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
              </el-option>
            </el-select>   
		    </el-form-item>

        <el-form-item label="评分比例" :label-width="formLabelWidth">
		      <el-input v-model="form.indicatorRatio" auto-complete="off"></el-input>
		    </el-form-item>

        <div v-for="(item,index) in form.brotherRelation" :key="index">
          <div class="borther">兄弟节点：{{index + 1}}</div>
          <el-form-item label="指标名称" :label-width="formLabelWidth">            
            <el-select
              v-model="item.indicatorUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入指标名称"
              :remote-method="remoteMethod"
              :loading="loading">
              <el-option
                v-for="item1 in indicatorOptions"
                :key="item1.uid"
                :label="item1.name"
                :value="item1.uid">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="评分比例" :label-width="formLabelWidth">
            <el-input v-model="item.indicatorRatio" auto-complete="off"></el-input>
          </el-form-item>
        </div>
		    
		  </el-form>
		  <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="addParamter">添加节点</el-button>
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="submitForm">确 定</el-button>
		  </div>
		</el-dialog>

  </div>
</template>

<script>
import {
  getRelationList,
  findBrother,
  addRelation,
  editRelation,
  deleteRelation
} from "@/api/professionIndicatorRelation";
import { getIndicatorList } from "@/api/indicator";
import { getProfessionList } from "@/api/profession";
import { formatData } from "@/utils/webUtils";
import { Loading } from "element-ui";
export default {
  data() {
    return {
      loadingInstance: null,
      loadingOption: {
        target: "#loadingBody",
        body: true,
        text: "正在加载中~"
      },
      tableData: [],
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      total: 0, //总数量
      title: "添加关系",
      dialogFormVisible: false, //控制弹出框
      formLabelWidth: "120px",
      isEditForm: false,
      indicatorOptions: [], //指标候选
      professionOptions: [], //专业候选
      loading: false,
      form: {
        uid: null,
        parentUid: null,
        professionUid: null,
        indicatorUid: null,
        indicatorRatio: null,
        grade: null,
        brotherRelation: []
      },
      searchValue: "",
      options: [
        {
          value: 0,
          label: "指标"
        },
        {
          value: 1,
          label: "观测点"
        }
      ]
    };
  },
  created() {
    this.loadingInstance = Loading.service(this.loadingOption);
    this.relationList();
  },
  watch: {
    "form.brotherRelation": {
      handler() {
        console.info("value changed ");
      },
      deep: true
      // immediate: true,
    }
  },
  methods: {
    getFormObject: function() {
      var formObject = {
        uid: null,
        professionUid: null,
        indicatorUid: null,
        parentUid: "",
        indicatorRatio: null,
        grade: null,
        brotherRelation: []
      };
      return formObject;
    },
    relationList: function() {
      var params = new URLSearchParams();
      params.append("keyword", this.keyword);
      params.append("page", this.currentPage - 1);
      params.append("size", this.pageSize);
      getRelationList(params).then(response => {
        if (response.code == "success") {
          console.log(response);
          this.tableData = response.data.content;
          this.total = response.data.total;
          this.pageSize = response.data.pageable.size;
          this.currentPage = response.data.pageable.page + 1;

          //给每个评分参考增加指标 和专业
          for (let a = 0; a < response.data.content.length; a++) {
            let tag1 = false;
            let tag2 = false;
            this.indicatorOptions.forEach(element => {
              if (element.uid == response.data.content[a].indicator.uid) {
                tag1 = true;
              }
            });

            this.professionOptions.forEach(element => {
              if (element.uid == response.data.content[a].profession.uid) {
                tag2 = true;
              }
            });

            if (!tag1) {
              this.indicatorOptions.push(response.data.content[a].indicator);
            }
            if (!tag2) {
              this.professionOptions.push(response.data.content[a].profession);
            }
          }
        }
        this.loadingInstance.close();
      });
    },
    addParamter: function() {
      // let borther = this.form.brotherRelation;
      // borther.push({ uid: "", indicatorUid: "", indicatorRatio: "" });
      let bb = { uid: "", indicatorUid: "", indicatorRatio: "" };
      // this.form.brotherRelation = [];
      // this.form.brotherRelation = borther;
      var lenght = this.form.brotherRelation.length;
      this.form.brotherRelation.splice(lenght + 1);
      this.form.brotherRelation.splice(lenght + 1, 1, bb);
      this.dialogFormVisible = false;
      this.dialogFormVisible = true;
    },
    //远程搜索方法
    remoteMethod(query) {
      if (query !== "") {
        console.log("开始了", query);
        this.loading = true;
        console.log("开始查找");
        var params = new URLSearchParams();
        params.append("keyword", query);
        getIndicatorList(params).then(response => {
          if (response.code == "success") {
            console.log(response);
            this.indicatorOptions = response.data.content;
          }
          this.loading = false;
        });
      } else {
        this.indicatorOptions = [];
      }
    },
    //专业远程搜索方法
    remoteMethod2(query) {
      if (query !== "") {
        console.log("开始了", query);
        this.loading = true;
        console.log("开始查找");
        var params = new URLSearchParams();
        params.append("keyword", query);
        getProfessionList(params).then(response => {
          if (response.code == "success") {
            console.log(response);
            this.professionOptions = response.data.content;
          }
          this.loading = false;
        });
      } else {
        this.professionOptions = [];
      }
    },
    handleCurrentChange: function(val) {
      console.log("页码改变", val);
      this.currentPage = val;
      this.relationList();
    },
    handleFind: function() {
      console.log("点击了查找");
      this.relationList();
    },
    handleAdd: function() {
      console.log("点击了添加");
      this.dialogFormVisible = true;
      this.form = this.getFormObject();
      this.isEditForm = false;
    },
    handleEdit: function(row) {
      this.title = "编辑关系";
      this.form = row;
      var params = new URLSearchParams();
      params.append("parentUid", this.form.parentUid);
      params.append("professionUid", this.form.professionUid);
      findBrother(params).then(response => {
        if (response.code == "success") {
          var data = response.data;
          var brotherRelation = [];
          for (let i = 0; i < data.length; i++) {
            if (data[i].uid != this.form.uid) {
              let indicatorUid = data[i].indicatorUid;
              let uid = data[i].uid;
              let indicatorRatio = data[i].indicatorRatio;
              brotherRelation.push({
                uid: uid,
                indicatorUid: indicatorUid,
                indicatorRatio: indicatorRatio
              });
            }
          }
          this.form.brotherRelation = [];
          this.form.brotherRelation = brotherRelation;
        }
        this.isEditForm = true;
        this.dialogFormVisible = true;
      });
    },
    handleDelete: function(row) {
      var params = new URLSearchParams();
      params.append("uid", row.uid);
      console.log("点击了删除", row);
      this.$confirm("删除时，请要删除的比例设置成0, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteRelation(params).then(response => {
            if (response.code == "success") {
              this.$message({
                type: "success",
                message: response.data
              });
              this.relationList();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    submitForm: function() {
      console.log("点击了提交", this.form);

      //如果父UID为空，那么父ID为专业ID
      var parentUid = "";
      if (this.form.parentUid == "") {
        parentUid = this.form.professionUid;
      } else {
        parentUid = this.form.parentUid;
      }
      var uid = this.form.uid;
      var professionUid = this.form.professionUid;
      var indicatorUid = this.form.indicatorUid;
      var indicatorRatio = this.form.indicatorRatio;
      var borther = this.form.brotherRelation;
      var json = [];
      json.push({
        uid: uid,
        parentUid: parentUid,
        indicatorUid: indicatorUid,
        professionUid: professionUid,
        indicatorRatio: indicatorRatio
      });
      console.log("兄弟节点", borther);
      if (borther) {
        for (var i = 0; i < borther.length; i++) {
          //  parentUid 和 profession都是通过 第一个传递来的
          json.push({
            uid: borther[i].uid,
            parentUid: parentUid,
            indicatorUid: borther[i].indicatorUid,
            professionUid: professionUid,
            indicatorRatio: borther[i].indicatorRatio
          });
        }
      }

      var params = new URLSearchParams();
      params.append("jsonArr", JSON.stringify(json));
      console.log("提交的JSON", json);
      if (this.isEditForm) {
        editRelation(params).then(response => {
          console.log(response);
          if (response.code == "success") {
            this.$message({
              type: "success",
              message: response.data
            });
          } else {
            this.$message({
              type: "error",
              message: response.data
            });
          }
          this.dialogFormVisible = false;
          this.relationList();
        });
      } else {
        addRelation(params).then(response => {
          console.log(response);
          this.$message({
            type: "success",
            message: response.data
          });
          this.dialogFormVisible = false;
          this.relationList();
        });
      }
    }
  }
};
</script>

<style>
.borther {
  width: 100%;
  height: 20px;
  text-align: center;
  margin: 10px 0 10px 0;
}
</style>
