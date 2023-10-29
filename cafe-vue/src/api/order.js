import request from '@/utils/request'

export function addOrder(data) {
  return request({
    url: '/order/add',
    method: 'post',
    data
  })
}
