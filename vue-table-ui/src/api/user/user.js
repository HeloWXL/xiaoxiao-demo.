import {get, post} from '@/util/axios'

/**
 * 查询接口
 * @param params
 * @returns {Promise<unknown>}
 */
export function queryUser(params) {
    return get('/user/selectAll', params)
}

/**
 * 新增、修改接口
 * @param params
 * @returns {Promise<unknown>}
 */
export function saveOrUpdateUser(params) {
    return post('/user/saveOrUpdate', params)
}

/**
 * 删除接口
 * @param params
 * @returns {Promise<unknown>}
 */
export function deleteUser(params) {
    return post('/user/delete', params)
}
