module Prj1 {
	requires javafx.controls;
	requires javafx.fxml;

	opens application to javafx.graphics, javafx.fxml;

	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;

	opens controller to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
	
}
