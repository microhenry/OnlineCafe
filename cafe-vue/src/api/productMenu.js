import request from '@/utils/requestNew'

//Get productMenu list
export function productMenuList(data, category) {
  return request({
    url: '/menu/' + category,
    method: 'post',
    data
  })
}
