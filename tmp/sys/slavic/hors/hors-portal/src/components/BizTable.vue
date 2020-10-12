<template>
  <div>
    <el-table
      v-loading="table.loading  != 'undefined' ? table.loading : dataDefault.table.loading"
      :data="table.list"
      stripe
      border
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :size="(table.options && table.options.size) ? table.options.size : dataDefault.table.options.size"
      empty-text="呀,,没有查询到数据呢..."
      ref="mutipleTable"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        v-if="(table.options && table.options.mutiSelect != 'undefined') ? table.options.mutiSelect : dataDefault.table.options.mutiSelect"
        type="selection"
        style="width: 55px;"
      >
      </el-table-column>
      <template v-for="(column, index) in table.columns">
        <el-table-column
          :prop="column.prop"
          :key='column.label'
          :label="column.label"
          :align="column.align"
          :width="column.width"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <template v-if="!column.render">
              <template v-if="column.formatter">
                <span v-html="column.formatter(scope.row, column)"></span>
              </template>
              <template v-else>
                <span>{{scope.row[column.prop]}}</span>
              </template>
            </template>
            <template v-else>
              <expand-dom
                :column="column"
                :row="scope.row"
                :render="column.render"
                :index="index"
              ></expand-dom>
            </template>
          </template>
        </el-table-column>
      </template>
      <el-table-column
        ref="fixedColumn"
        label="操作"
        align="center"
        :width="table.operates.width"
        :fixed="table.operates.fixed"
        v-if="table.operates && table.operates.list && table.operates.list.filter(_x=>_x.show === true).length > 0"
      >
        <template slot-scope="scope">
          <div>
            <template v-for="(btn, key) in table.operates.list">
              <el-button
                :key='key'
                v-if="btn.show"
                :type="btn.type"
                size="mini"
                :icon="btn.icon"
                :disabled="btn.disabled != 'undefined' ? btn.disabled : false"
                :plain="btn.plain != 'undefined' ? btn.plain : false"
                @click.native.prevent="btn.method(key,scope.row)"
              >{{ btn.label }}
              </el-button>
            </template>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: 'BizTable',
  data() {
    return {
      dataDefault: {
        table: {
          highlightCurrentRow: false,
          border: true,
          loading: false,
          options: {
            size: 'mini',
            stripe: false,
            mutiSelect: false
          }
        }
      },
      multipleSelection: []
    }
  },
  props: {
    table: {
      type: Object,
      default: () => { }
    }
  },
  components: {
    expandDom: {
      functional: true,
      props: {
        row: Object,
        render: Function,
        index: Number,
        column: {
          type: Object,
          default: null
        }
      },
      render: (h, ctx) => {
        const params = {
          row: ctx.props.row,
          index: ctx.props.index
        }
        if (ctx.props.column) {
          params.column = ctx.props.column
        }
        return ctx.props.render(h, params)
      }
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
      this.$emit('handleSelectionChange', val)
    }
  }
}
</script>