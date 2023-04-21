package io.github.melin.superior.parser.oracle

import io.github.melin.superior.common.StatementType
import io.github.melin.superior.common.relational.TableId
import io.github.melin.superior.common.relational.dml.DeleteTable
import io.github.melin.superior.common.relational.dml.QueryStmt
import io.github.melin.superior.common.relational.dml.UpdateTable
import org.junit.Assert
import org.junit.Test

class OracleSqlParserDmlTest {
    @Test
    fun querySqlTest0() {
        val sql = """
            SELECT CUSTOMER_NAME, PRICE FROM FLINKUSER.ORDERS;
        """.trimIndent()

        val statementData = OracleSqlHelper.getStatementData(sql)
        val statement = statementData.statement
        if (statement is QueryStmt) {
            Assert.assertEquals(StatementType.SELECT, statementData.type)
            Assert.assertEquals(1, statement.inputTables.size)
            Assert.assertEquals(TableId("FLINKUSER", "ORDERS"), statement.inputTables.get(0))
        } else {
            Assert.fail()
        }
    }

    @Test
    fun deleteTest() {
        val sql = """
            DELETE FROM films
            WHERE producer_id IN (SELECT id FROM producers WHERE name = 'foo');
        """.trimIndent()

        val statementData = OracleSqlHelper.getStatementData(sql)
        val statement = statementData.statement
        if (statement is DeleteTable) {
            Assert.assertEquals(StatementType.DELETE, statementData.type)
            Assert.assertEquals("films", statement.firstTableId().tableName)
            Assert.assertEquals(1, statement.inputTables.size)
        } else {
            Assert.fail()
        }
    }

    @Test
    fun updateTest0() {
        val sql = """
            UPDATE employees SET sales_count = sales_count + 1 WHERE id =
            (SELECT sales_person FROM accounts WHERE name = 'Acme Corporation');
        """.trimIndent()

        val statementData = OracleSqlHelper.getStatementData(sql)
        val statement = statementData.statement
        if (statement is UpdateTable) {
            Assert.assertEquals(StatementType.UPDATE, statementData.type)
            Assert.assertEquals("employees", statement.firstTableId().tableName)
            Assert.assertEquals(1, statement.inputTables.size)
        } else {
            Assert.fail()
        }
    }
}