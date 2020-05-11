import React, { Component } from 'react'
import { 
  Row,
  Col,
  Input,
  Button,
  Select,
  Form,
  DatePicker,
  TreeSelect
} from 'antd';
import moment from 'moment';
import { connect } from 'dva';
import tableStyles from '@/style/TableList.less';
import {selfAdaption} from '@/utils/utils'
const {inputConfig,timeConfig,formItemConfig,searchConfig,colConfig} = selfAdaption()

const FormItem = Form.Item
const { RangePicker } = DatePicker
const dateFormat = 'YYYY-MM-DD'
const Option = Select.Option;

@connect()

@Form.create()

export default class FilterIpts extends Component {
  formSubmit = async (e) => {
    await this.props.dispatch({
      type: '${table.entityPath}Manage/setSearchInfo',
      payload: this.getFormValue(),
    })
    this.props.getList(1, this.props.pageSize)
  }
  //   获取表单信息
  getFormValue = () => {
    let formQueryData = this.props.form.getFieldsValue();
    return formQueryData;
  }
  reset = () => {
    this.props.form.resetFields()
  }
  componentDidMount() {
    this.props.getChild(this)
  }
  componentWillUnmount() {
    this.props.dispatch({
      type: '${table.entityPath}Manage/setSearchInfo',
      payload: {},
    })
  }
  render() {
    const { form: { getFieldDecorator }, searchWholeState } = this.props
    const formConfig = {
      labelCol:  { span: 7 },
      wrapperCol: { span: 17 }
    }
    return (
      <div className={tableStyles.tableList}>
        <div className={tableStyles.tableListForm}>
          <Form>
            <Row gutter={24} type="flex">
              <#list table.fields as field>
                <Col <#if field_index gt 2> style={searchWholeState ? { display: 'none' } : {}} </#if> {...inputConfig}>
                  <FormItem label="${field.comment}" {...formItemConfig}>
                    {getFieldDecorator('${field.propertyName}')(
                      <Input />
                    )}
                  </FormItem>
                </Col>
                </#list>
                <Col {...searchConfig}>
                  <FormItem {...formItemConfig}>
                    <Button onClick={this.formSubmit} type="primary">搜索</Button>
                    <Button onClick={this.reset} style={{ marginLeft: 8 }}>清空</Button>
                  </FormItem>
                </Col>
            </Row>
          </Form>
        </div>
      </div>
    )
  }
}
