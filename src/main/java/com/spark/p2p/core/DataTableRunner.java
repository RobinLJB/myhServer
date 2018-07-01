package com.spark.p2p.core;

import java.sql.SQLException;

import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;

public interface DataTableRunner {
	public DataTable run(DataTableRequest params) throws SQLException;
}
