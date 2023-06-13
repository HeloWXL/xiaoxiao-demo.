# 树形结构数据

> 应用场景

比如我们需要构建菜单、机构树、其他业务类型树形结构

## 工具类

1. 我们可以把菜单列表返回，获取parent_id字段等于0的节点，称为根节点，这样的节点代表一级菜单
2. 再通过根节点的主键去寻找子菜单，因为要有多及菜单，所以要用递归构建子树，直到没有子菜单为止
3. 最后通过构建完整的菜单树

> 返回结果示例

只展示部分数据

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "parentId": 0,
      "menuName": "系统管理",
      "menuIcon": "el-icon-setting",
      "childrenList": [
        {
          "id": 2,
          "parentId": 1,
          "menuName": "用户管理",
          "menuIcon": "el-icon-service",
          "childrenList": [
            {
              "id": 3,
              "parentId": 2,
              "menuName": "查看",
              "menuIcon": null,
              "childrenList": []
            },
            {
              "id": 4,
              "parentId": 2,
              "menuName": "新增",
              "menuIcon": null,
              "childrenList": []
            },
            {
              "id": 5,
              "parentId": 2,
              "menuName": "修改",
              "menuIcon": null,
              "childrenList": []
            },
            {
              "id": 6,
              "parentId": 2,
              "menuName": "删除",
              "menuIcon": null,
              "childrenList": []
            }
          ]
        }
      ]
    }
  ],
  "msg": "执行成功"
}

```




## SQL

通过SQL的方式的话，只能查询出两层，如下所示：

> 返回结果示例

只展示部分数据

```json
{
    "code": 0,
    "data": [
        {
            "id": 1,
            "parentId": 0,
            "menuName": "系统管理",
            "menuIcon": "el-icon-setting",
            "childrenList": [
                {
                    "id": 2,
                    "parentId": 1,
                    "menuName": "用户管理",
                    "menuIcon": "el-icon-service",
                    "childrenList": null
                }
            ]
        }
    ],
    "msg": "执行成功"
}
```



