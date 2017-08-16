Experimental Excel ORM library

#### Limitations
* Every sheet maps to only _ONE_ POJO.
* Sadly, every time you need to update any data, whole sheet of the excel will be rewritten. This will be a major problem if data is huge. 
* Only String, Integer, Double, BigInteger, Long, Boolean and Date classes are supported.


***


<table>
  <tr>
    <th>Name</th>
    <th>Last Name</th>
    <th>Age</th>
    <th>Amount</th>
    <th>Description</th>
    <th>Date</th>
  </tr>
  <tr>
    <td>Name1</td>
    <td>Last Name1</td>
    <td>11</td>
    <td>101.1</td>
    <td>description1</td>
    <td>16/09/2017 19:30</td>
  </tr>
  <tr>
    <td>Name2</td>
    <td>Last Name2</td>
    <td>12</td>
    <td>102.2</td>
    <td>description2</td>
    <td>16/09/2017 19:30</td>
  </tr>
  <tr>
    <td>Name3</td>
    <td>Last Name3</td>
    <td>13</td>
    <td>103.3</td>
    <td>description3</td>
    <td>16/09/2017 19:30</td>
  </tr>
  <tr>
    <td>Name4</td>
    <td>Last Name4</td>
    <td>14</td>
    <td>104.4</td>
    <td>description4</td>
    <td>16/09/2017 19:30</td>
  </tr>
</table>


```java
@Excel(firstRow = 1, firstCol = 0, sheetName = "Tasks")
@Data
public class Task extends BaseExcel
{
    @ExcelColumn(col = 0 , columnName = "Name")
    private String firstName;

    @ExcelColumn(col = 1 , columnName = "Last Name")
    private String lastName;

    @ExcelColumn(col = 2 , columnName = "Age")
    private Integer age;

    @ExcelColumn(col = 3 , columnName = "Amount")
    private Double amount;

    @ExcelColumn(col = 4 , columnName = "Description")
    private String description;
    
    @ExcelColumn(col = 5, columnName = "Date", dateFormat = "dd/MM/yyyy HH:mm")
    private Date date;
}
```

```java
public class Test
{
    public static void main(String[] args)
    {
        ExcelReader excelReader = new ExcelReader();
        List<Task> taskList = excelReader.read(new File("/some/path/Excel.xlsx"), Task.class);
        //
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.write(new File("/some/path/Excel_new.xlsx"),taskList,Task.class);
    }
}
```