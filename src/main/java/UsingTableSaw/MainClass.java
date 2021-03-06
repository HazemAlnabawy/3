package UsingTableSaw;


import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import tech.tablesaw.api.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainClass {
    public static void main(String[] args) throws  IOException{
        // Dealing with titanic Data   Loadiing Data
        Table titanic = Table.read().csv("src/main/resources/data/titanic.csv");


        // mapping text data to numeric values on the sex field female=1,male=0 and adding a column named gender

        StringColumn strSexColumn = titanic.stringColumn("sex") ;
        List<Number> lstSexNumerical = new ArrayList<>() ;
        for (String val:strSexColumn) {
            if (val.equals("male"))
                lstSexNumerical.add(0);
            else if (val.equals("female"))
                lstSexNumerical.add(1);
        }



        /////////////////////////////////////////// Inserting a column into the Data
        NumberColumn numMappedCol =null;
        numMappedCol = DoubleColumn.create("Gendre New Column",lstSexNumerical);
        titanic.insertColumn(4,numMappedCol);
        //printing the head of the data set
        System.out.println(titanic.first(5).toString());



        //////////////////////////////////////////// Printing summary data
        String summaryAboutData = titanic.summary().toString() ;
        System.out.println(summaryAboutData);
        //Another Data Set
        Table anotherTitanic = Table.read().csv("src/main/resources/data/titanic.csv");
        System.out.println(anotherTitanic.first(20));



        //////////////////////////////////////////// adding column from one to another
        StringColumn strCol = StringColumn.create("New Sex",((StringColumn)titanic.column(3)).asList().stream());
        anotherTitanic.insertColumn(4,strCol);
        System.out.println(anotherTitanic.first(5));

        //merging two columns
        System.out.println(anotherTitanic.row(0).toString());


        /////////////////////////////////////////// using XChart to plot the data

        List xValues =  titanic.column(9).asList();
        List yValues =  titanic.column(5).asList();



        XYChart chart = new XYChartBuilder().width(600).height(500).title("Titanic").xAxisTitle("X").yAxisTitle("Y").build();

        chart.addSeries("Tit",xValues,yValues);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setMarkerSize(8);

        new SwingWrapper<>(chart).displayChart();

    }

}
