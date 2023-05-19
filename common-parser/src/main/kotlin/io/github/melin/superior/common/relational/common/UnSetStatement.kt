package io.github.melin.superior.common.relational.common

import io.github.melin.superior.common.PrivilegeType
import io.github.melin.superior.common.SqlType
import io.github.melin.superior.common.relational.Statement

data class UnSetStatement(
    val key: String
) : Statement() {
    override val privilegeType: PrivilegeType = PrivilegeType.OTHER
    override val sqlType: SqlType = SqlType.TCL
}