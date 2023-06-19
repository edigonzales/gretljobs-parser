package ch.so.agi.gretl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

public class App {
    private static final String gretlJobsFolder = "/Users/stefan/sources/gretljobs/";
    
    public static void main(String[] args) throws IOException {
        
        List<Path> sqlPaths = new ArrayList<Path>();
        try (Stream<Path> walk = Files.walk(Paths.get(gretlJobsFolder))) {
            sqlPaths = walk
                    .filter(p -> !Files.isDirectory(p))   
                    .filter(f -> isEndWith(f.toString()))
                    .collect(Collectors.toList());        
        }

        
        System.out.println(sqlPaths.size());
        System.out.println("Hallo Welt.");
        
        for (Path sqlPath : sqlPaths) {
            String content = Files.readString(sqlPath);

            Statements stmts;
            try {
                stmts = CCJSqlParserUtil.parseStatements(content);
            } catch (JSQLParserException e) {
                System.out.println("file: " + sqlPath);
//                if (sqlPath.toString().contains("afu_oekomorphologie_csvimport")) {
//                    System.out.println(e.getMessage());  
//                }
                System.out.println(e.getMessage());
                continue;
            }
            for (Statement stmt : stmts.getStatements()) {

            }
        }
        
        
        
    }
    
    private static boolean isEndWith(String file) {
        if (file.toLowerCase().endsWith("sql")) {
            return true;
        }
        return false;
    }
}
