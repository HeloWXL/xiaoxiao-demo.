


1. 我们可以把菜单列表返回，获取parent_id字段等于0的节点，称为根节点，这样的节点代表一级菜单
2. 再通过根节点的主键去寻找子菜单，因为要有多及菜单，所以要用递归构建子树，直到没有子菜单为止
3. 最后通过构建完整的菜单树



