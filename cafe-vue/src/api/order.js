import request from '@/utils/request'

export function addOrder(data) {
  return request({
    url: '/order/add',
    method: 'post',
    data
  })
}

export function getOrderList(data) {
  return request({
    url: '/user/order/list',
    method: 'post',
    data
  })
}

export function getCustomerOrderList(data) {
  return request({
    url: '/order/list',
    method: 'post',
    data
  })

}

export function getOrderDetail(data) {
  return request({
    url: '/order/list/detail',
    method: 'post',
    data
  })
}

export function updateOrder(data) {
  return request({
    url: '/order/update',
    method: 'post',
    data
  })
}

export function deleteOrder(id) {
  return request({
    url: '/order/delete',
    method: 'post',
    params: {
      id
    }
  })
}

export function batchDeleteOrder(data) {
  return request({
    url: '/order/delete/batch',
    method: 'post',
    data
  })
}
