/*
var db = openDatabase('myDb', '', 'myTable', 1024 * 1024);
var create_table_sql = 'create table if not exists myTable(id integer primary key, name text, code text, spareNum text, wayNum text, needNum text, desc text)';
var drop_table_sql = 'drop table if exists myTable';
var insert_sql = 'insert into myTable values(?,?,?,?,?,?,?)';
var delete_sql = 'delete from myTable where id = ?';
var update_sql = 'update myTable set name = ?, code = ?, spareNum = ?, wayNum = ?, needNum = ?, desc = ? where id = ?';
var select_sql = 'select * from myTable';
var select_id_sql = 'select * from myTable where id = ?';
*/

/**
 * TODO:创建数据表
 */
function dbCreateTable(sql, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			[],
			function(tx, rs) { if(success){success(tx, rs);} },
			function(tx, ex) { if(error){error(tx, ex);} }
		);
	});
}

/**
 * TODO:删除数据表
 */
function dbDropTable(sql, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			[],
			function(tx, rs) { if(success){success(tx, rs);} },
			function(tx, ex) { if(error){error(tx, ex);} }
		);
	});
}

/**
 * TODO:添加数据
 */
function dbAddData(sql, arr, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			arr,
			function(tx, rs) { if(success){success(tx, rs);} },
			function(tx, ex) { if(error){error(tx, ex);} }
		);
	});
}

/**
 * TODO:删除数据
 */
function dbDelData(sql, id, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			id,
			function(tx, rs) { if(success){success(tx, rs);} },
			function(tx, ex) { if(error){error(tx, ex);} }
		);
	});
}

/**
 * TODO:更新数据
 */
function dbUpdateData(sql, arr, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			arr,
			function(tx, rs) { if(success){success(tx, rs);} },
			function(tx, ex) { if(error){error(tx, ex);} }
		);
	});
}

/**
 * TODO:查询所有数据
 */
function dbShowData(sql, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			[],
			function(tx, rs) { if(success) {success(tx, rs);} },
			function(tx, ex) { if(error) {error(tx, ex);} }
		);
	});
}

/**
 * TODO:查询指定数据
 */
function dbShowDataById(sql, id, success, error) {
	db.transaction(function(ts) {
		ts.executeSql(
			sql,
			id,
			function(tx, rs) { if(success) {success(tx, rs);} },
			function(tx, ex) { if(error) {error(tx, ex);} }
		);
	});
}