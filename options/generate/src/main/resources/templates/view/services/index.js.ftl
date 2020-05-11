import { stringify } from 'qs';
import request from '@/utils/request';
import _baseApi from '@/utils/_baseApi';
import exportExcel from '@/utils/exportExcel';

// 测试列表
export async function mockList(params) {
  return request(_baseApi + '/${table.entityPath}/list', {
    method: 'POST',
    body: params
  });
}

//   添加
export async function addManage(params) {
  return request(_baseApi + '/${table.entityPath}/add', {
    method: 'POST',
    body: params
  });
}

//   修改
export async function modifyManage(params) {
  return request(_baseApi + '/${table.entityPath}/update', {
    method: 'POST',
    body: params
  });
}

//   删除
export async function deleteManage(params) {
  return request(_baseApi + '/${table.entityPath}/delete', {
    method: 'POST',
    body: params
  });
}

//    获取详情
export async function getModifyInfo(params) {
  return request(_baseApi + '/${table.entityPath}/info', {
    method: 'POST',
    body: params
  });
}

<#if cfg.isExport>
// 导出
export async function exportFile(params) {
  return exportExcel(_baseApi + '/${table.entityPath}/export', {
    method: 'POST',
    body: params
  });
}
</#if>