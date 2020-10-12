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
        :remote-method="remoteMethod1"
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

	    <el-table-column label="指标名称" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.indicator.name }}</span>
	      </template>
	    </el-table-column>
	    
      <el-table-column label="专业名称" width="150">
	      <template slot-scope="scope">
	        <span>{{ scope.row.profession.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="评价参考" width="250">
	      <template slot-scope="scope">
	        <span>{{ scope.row.reference.name }}</span>
	      </template>
	    </el-table-column>

      <el-table-column label="分数" width="100">
	      <template slot-scope="scope">
	        <span>{{ scope.row.grade }}</span>
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
		  	
        <el-form-item label="观测点名称" :label-width="formLabelWidth">
		        <el-select
              v-model="form.indicatorUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入观测点名称"
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
              :remote-method="remoteMethod1"
              :loading="loading">
              <el-option
                v-for="item in professionOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
              </el-option>
            </el-select>
		    </el-form-item>

        <el-form-item label="评价参考" :label-width="formLabelWidth">
		      	<el-select
              v-model="form.referenceUid"
              clearable
              size="small"     
              filterable
              remote              
              reserve-keyword
              placeholder="请输入评价参考名称"
              :remote-method="remoteMethod2"
              :loading="loading">
              <el-option
                v-for="item in indicatorGradeReferenceOptions"
                :key="item.uid"
                :label="item.name"
                :value="item.uid">
              </el-option>
            </el-select>
		    </el-form-item>

		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="submitForm">确 定</el-button>
		  </div>
		</el-dialog>

  </div>
</template>

<script>
import {
  getIndicatorGradeList,
  addIndicatorGrade,
  editIndicatorGrade,
  deleteIndicatorGrade
} from "@/api/indicatorGrade";

import { getIndicatorList } from "@/api/indicator";
import { getProfessionList } from "@/api/profession";
import { getIndicatorGradeReferenceList } from "@/api/indicatorGradeReference";
import { formatData } from "@/utils/webUtils";
import { Loading } from "element-ui";
export default {
  data() {
    return {
      tableData: [],
      indicatorOptions: [], //指标候选
      professionOptions: [], //专业候选
      indicatorGradeReferenceOptions: [], //评分参考候选
      loadingInstance: null,
      loading: false, //用于控制远程搜索加载
      loadingOption: {
        target: "#loadingBody",
        body: true,
        text: "正在加载中~"
      },
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      total: 0, //总数量
      title: "添加观测点分数",
      dialogFormVisible: false, //控制弹出框
      formLabelWidth: "120px",
      isEditForm: false,
      form: {
        uid: null,
        indicatorUid: null,
        referenceUid: null,
        grade: null
      }
    };
  },
  created() {
    this.loadingInstance = Loading.service(this.loadingOption);
    this.indicatorGradeList();

  },
  methods: {
    indicatorGradeList: function() {
      var params = new URLSearchParams();
      params.append("page", this.currentPage - 1);
      params.append("size", this.pageSize);
      params.append("professionUid", this.keyword);
      getIndicatorGradeList(params).then(response => {
        if (response.code == "success") {
          console.log(response);
          this.tableData = response.data.content;

          this.total = response.data.total;
          this.pageSize = response.data.pageable.size;
          this.currentPage = response.data.pageable.page + 1;

          //给每个评分参考增加指标 和专业
          for (let a = 0; a < response.data.length; a++) {
            let tag1 = false;
            let tag2 = false;
            let tag3 = false;
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

            this.indicatorGradeReferenceOptions.forEach(element => {
              if (element.uid == response.data.content[a].reference.uid) {
                tag3 = true;
              }
            });
            if (!tag1) {
              this.indicatorOptions.push(response.data.content[a].indicator);
            }
            if (!tag2) {
              this.professionOptions.push(response.data.content[a].profession);
            }
            if (!tag3) {
              this.indicatorGradeReferenceOptions.push(
                response.data.content[a].reference
              );
            }
          }                    
        }
        this.loadingInstance.close();
      });
    },
    handleCurrentChange: function(val) {
      console.log("页码改变", val);
      this.currentPage = val ;
      this.indicatorGradeList();
    },
    getFormObject: function() {
      var formObject = {
        uid: null,
        indicatorUid: null,
        referenceUid: null,
        grade: null
      };
      return formObject;
    },
    handleFind: function() {
      console.log("点击了查找");
      this.indicatorGradeList();
    },
    //远程搜索方法 By 指标点
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        var params = new URLSearchParams();
        params.append("keyword", query);
        params.append("typeStatus", 1); //只查找观测点
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
    //远程搜索方法 By 专业
    remoteMethod1(query) {
      if (query !== "") {
        this.loading = true;
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
    //远程搜索方法 By 评价参考
    remoteMethod2(query) {
      if (query !== "") {
        this.loading = true;
        var params = new URLSearchParams();
        params.append("keyword", query);
        getIndicatorGradeReferenceList(params).then(response => {
          if (response.code == "success") {
            this.indicatorGradeReferenceOptions =
              response.data.indicatorGradeReference.content;
          }
          this.loading = false;
        });
      } else {
        this.indicatorGradeReferenceOptions = [];
      }
    },
    handleAdd: function() {
      console.log("点击了添加");
      this.dialogFormVisible = true;
      this.form = this.getFormObject();
      this.isEditForm = false;
    },
    handleEdit: function(row) {
      console.log("点击了编辑");
      this.title = "编辑观测点分数";
      this.dialogFormVisible = true;
      this.form = row;
      this.isEditForm = true;
    },
    handleDelete: function(row) {
      var params = new URLSearchParams();
      params.append("uid", row.uid);
      console.log("点击了删除", row);
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteIndicatorGrade(params).then(response => {
            if (response.code == "success") {
              this.$message({
                type: "success",
                message: response.data
              });
              this.indicatorGradeList();
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
      var that = this;
      if (
        !this.form.indicatorUid ||
        !this.form.professionUid ||
        !this.form.referenceUid
      ) {
        this.$message({
          type: "error",
          message: "必填项不能为空"
        });
        return;
      }

      var params = new URLSearchParams();
      params.append("referenceUid", this.form.referenceUid);
      params.append("professionUid", this.form.professionUid);
      params.append("indicatorUid", this.form.indicatorUid);
      this.indicatorGradeReferenceOptions.forEach(element => {
        if (element.uid == that.form.referenceUid) {
          params.append("grade", element.grade);
        }
      });

      if (this.isEditForm) {
        params.append("uid", this.form.uid);
        editIndicatorGrade(params).then(response => {
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
          this.indicatorGradeList();
        });
      } else {
        addIndicatorGrade(params).then(response => {
          console.log(response);
          this.$message({
            type: "success",
            message: response.data
          });
          this.dialogFormVisible = false;
          this.indicatorGradeList();
        });
      }
    }
  }
};
</script>
