import {get,download} from '@/util/axios'

export function queryUserFile(params){
    return get('/uploadFile/selectAll',params)
}

/**
 * 下载文件
 * @param params
 * @returns {Promise | Promise<unknown>}
 */
export function downloadFile(params){
    return download('/upload/downloadFile',params)
}

/**
 * 在线预览文件
 * @param params
 * @returns {Promise | Promise<unknown>}
 */
export function previewFile(params){
    return download('/upload/previewFile',params)
}