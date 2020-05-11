import React, { PureComponent } from 'react';
import { Modal, Form, Input, message } from 'antd';
import { connect } from 'dva';

const FormItem = Form.Item;

@Form.create()
@connect(({ ${table.entityPath}Manage, loading }) => ({
  ${table.entityPath}Manage,
  submitLoading: loading.effects['${table.entityPath}Manage/modifyManage']
}))
export default class Modify extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      value: undefined,
    };
  }
  changeVisible = visible => {
    if (!visible) {
      this.props.dispatch({
        type: '${table.entityPath}Manage/setModifyInfo',
        payload: {}
      })
    }
    this.setState({
      visible,
    });
  };
  handleOk = async () => {
    const { dispatch, form, ${table.entityPath}Manage: { modifyInfoData } } = this.props;
    form.validateFieldsAndScroll(async(err, values) => {
      if (!err) {
        let res
        if (modifyInfoData.id) {
          res = await dispatch({
            type: '${table.entityPath}Manage/modifyManage',
            payload: {
              ...values,
              id: this.props.${table.entityPath}Manage.modifyInfoData.id,
            }
          })
        } else {
          res = await dispatch({
            type: '${table.entityPath}Manage/addManage',
            payload: values
          })
        }
        if (res && res.status === 1) {
          this.changeVisible(false)
          message.success(res.statusDesc)
          this.props.getList(this.props.currPage, this.props.pageSize)
        } else message.error(res.statusDesc)
      }
    });
  };
  componentDidMount() {
    this.props.getChildData(this);
  }
  render() {
    const { form: { getFieldDecorator }, ${table.entityPath}Manage: { modifyInfoData } } = this.props;
    const formConfig = {
      labelCol: { span: 5 },
      wrapperCol: { span: 18 },
    };
    return (
      <Modal
        title={modifyInfoData.id ? '修改' : '添加'}
        bodyStyle={{ maxHeight: 470, overflow: 'auto' }}
        visible={this.state.visible}
        onOk={this.handleOk}
        maskClosable={false}
        destroyOnClose={true}
        onCancel={() => this.changeVisible(false)}
      >
        <Form>
          <#list table.fields as field>
          <FormItem
            label="${field.comment}"
            {...formConfig}
          >
            {getFieldDecorator('${field.propertyName}', {
              rules: [{ required: true, message: '请输入${field.comment}' }],
              initialValue: modifyInfoData && modifyInfoData.${field.propertyName}
            })(
              <Input placeholder={'请输入${field.comment}'} />
            )}
          </FormItem>
          </#list>
        </Form>
      </Modal>
    );
  }
}
