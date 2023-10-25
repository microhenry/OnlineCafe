import request from '@/utils/request'

export function cartNumber(data, userId) {
  return request({
    url: '/cart/cartNumber/' + userId,
    method: 'post',
    data
  })
}

export function cartList(data) {
  return request({
    url: '/cart/list',
    method: 'post',
    data
  })
}

export function addCart(data) {
  return request({
    url: '/cart/add',
    method: 'post',
    data
  })
}

export function updateCart(data) {
  return request({
    url: '/cart/update',
    method: 'post',
    data
  })
}

export function deleteCart(id) {
  return request({
    url: '/cart/delete',
    method: 'post',
    params: {
      id
    }
  })
}

export function batchDeleteCart(data) {
  return request({
    url: '/cart/delete/batch',
    method: 'post',
    data
  })
}
