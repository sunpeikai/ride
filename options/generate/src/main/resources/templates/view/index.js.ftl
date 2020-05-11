import React, { PureComponent, Fragment } from 'react';
import { Card, Button, Icon, Pagination, Table, Menu, Dropdown, message } from 'antd';
import { connect } from 'dva';
import Swal from 'sweetalert2';

import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import StandardTable from '@/components/StandardTable';
import permission from '@/utils/PermissionWrapper';
<#if cfg.isExport>
import ExportLoading from '@/components/ExportLoading'
</#if>

import SetColumns from '@/components/SetColumns';

// 添加/修改表单
import ModifyForm from './ModifyForm';
//   检索条件
import FilterIpts from './FilterIpts';

const plainOptions = [{
    label: '序号',
    value: 'key'
  }
<#list table.fields as field>
  , {
        label: '${field.comment}',
        value: '${field.propertyName}',
      }
</#list>
];
const defcolumns = [{
    title: '序号',
    dataIndex: 'key',
  }
<#list table.fields as field>
  , {
        title: '${field.comment}',
        dataIndex: '${field.propertyName}',
      }
</#list>
];

@permission

@connect(({ ${table.entityPath}Manage, loading }) => ({
  ${table.entityPath}Manage,
  loading: loading.effects['${table.entityPath}Manage/fetchList'] || loading.effects['${table.entityPath}Manage/getModifyInfo'],
  <#if cfg.isExport>
  exportLoading: loading.effects['orderManage/exportExcel']
  </#if>
}))

export default class template extends PureComponent {
  constructor(props) {
    super(props)
  }
  state = {
    currPage: 1,
    pageSize: 10,
    title: '添加',
    initColumns: ['key'
        <#list table.fields as field>
          ,'${field.propertyName}'
        </#list>
    ],
    syncColumns: [],
    staticColumns: [
      {
        title: '操作',
        render: (record) => {
          const action = (
            <Menu>
                <Menu.Item onClick={() => this.modifyHandler(record.id)}>
                <Icon type="edit" />修改
                </Menu.Item>
                <Menu.Item onClick={() => this.deleteHandler(record.id)}>
                <Icon type="close" />删除
                </Menu.Item>
            </Menu>
          )
          return (
            <Dropdown overlay={action}>
                <a className="ant-dropdown-link" href="#">
                    操作<Icon type="down" />
                </a>
            </Dropdown>
          )
        }
      }
    ],
    searchWholeState: false,
  }
  //   页数改变时
  onChange = (currPage) => {
    this.setState({
      currPage,
    }, () => {
      this.getList(currPage, this.state.pageSize)
    })
  }
  onShowSizeChange = (currPage, pageSize) => {
    this.setState({
      currPage,
      pageSize
    }, () => {
      this.getList(currPage, pageSize)
    })
  }
  getList = (currPage, pageSize) => {
    this.props.dispatch({
      type: '${table.entityPath}Manage/fetchList',
      payload: {
        currPage,
        pageSize,
        ...this.props.${table.entityPath}Manage.searchInfo
      }
    })
  }
  renderBtn = () => {
    const { searchWholeState } = this.state
    return (
      <Fragment>
          <Button onClick={() => this.setState({ searchWholeState: !this.state.searchWholeState })}>{searchWholeState ? '合并' : '展开' + '详细搜索'}</Button>
          <SetColumns
                  plainOptions={plainOptions}
                  defcolumns={defcolumns}
                  initColumns={this.state.initColumns}
                  staticColumns={this.state.staticColumns}
                  syncChangeColumns={this.syncChangeColumns}
          />
        <Button onClick={() => this.modifyChild.changeVisible(true)}><Icon type="plus" />添加</Button>
        <#if cfg.isExport>
         <ExportLoading exportLoading={this.props.exportLoading} exportExcel={this.exportExcel}>导出</ExportLoading>
        </#if>
      </Fragment>
    )
  }

  <#if cfg.isExport>
   exportExcel = () => {
    const { dispatch, form } = this.props;
    let formData = this.child.getFormValue()

    dispatch({
      type: '${table.entityPath}Manage/exportExcel',
      payload: formData
    });
  }
  </#if>
  // 修改
  modifyHandler = async (id) => {
    const res = await this.props.dispatch({
      type: '${table.entityPath}Manage/getModifyInfo',
      payload: {
        id
      }
    })
    if (res && res.status === 1) {
      this.modifyChild.changeVisible(true)
    } else {
      message.error(res.statusDesc)
    }
  }
  // 删除
  async deleteHandler(id) {
    const confirmVal = await Swal.fire({
      text: '确定要删除角色吗？',
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    if (confirmVal.value) {
      const res = await this.props.dispatch({
        type: '${table.entityPath}Manage/deleteManage',
        payload: {
          id
        }
      })
      if (res && res.status === 1) {
        message.success(res.statusDesc)
        this.getList(this.state.currPage, this.state.pageSize)
      } else {
        message.error(res.statusDesc)
      }
    }
  }

  <#if cfg.isExport>
  getChild = ref => this.child = ref
  </#if>
  componentDidMount() {
    this.syncChangeColumns([...defcolumns, ...this.state.staticColumns])
    this.getList(this.state.currPage, this.state.pageSize)
  }
  syncChangeColumns = (array = []) => {
    this.setState({
      syncColumns: array
    })
  }

  render() {
    const { permission, ${table.entityPath}Manage: { list } } = this.props;
    const { currPage, pageSize, data, selectedRows } = this.state;
    const values = {
      columns: this.state.syncColumns,
      data: {
        list,
      },
      loading: this.props.loading,
      pagination: {
        showTotal: (total) => ('共 ' + total + ' 条'),
        defaultCurrent: currPage,
        defaultPageSize: pageSize,
        total: list.length,
        showQuickJumper: true,
        showSizeChanger: true,
        pageSizeOptions: ['10', '20', '30', '40'],
        onChange: this.onChange,
        onShowSizeChange: this.onShowSizeChange
      },
    }
    return (
      <PageHeaderWrapper renderBtn={this.renderBtn}>
        <Card bordered={false}>
          <FilterIpts searchWholeState={this.state.searchWholeState} getList={this.getList} getChild={this.getChild} pageSize={pageSize} />
          <StandardTable 
            {...values}
          /> 
        </Card>
        <ModifyForm
          getChildData={(child) => this.modifyChild = child}
          getList={this.getList}
          currPage={currPage}
          pageSize={pageSize}
        />
      </PageHeaderWrapper>
    )
  }
}