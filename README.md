Experimental Excel ORM library


<table>
  <tr>
    <th>Name</th>
    <th>Last Name</th>
    <th>Age</th>
    <th>Amount</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>Name1</td>
    <td>Last Name1</td>
    <td>11</td>
    <td>101.1</td>
    <td>description1</td>
  </tr>
  <tr>
    <td>Name2</td>
    <td>Last Name2</td>
    <td>12</td>
    <td>102.2</td>
    <td>description2</td>
  </tr>
  <tr>
    <td>Name3</td>
    <td>Last Name3</td>
    <td>13</td>
    <td>103.3</td>
    <td>description3</td>
  </tr>
  <tr>
    <td>Name4</td>
    <td>Last Name4</td>
    <td>14</td>
    <td>104.4</td>
    <td>description4</td>
  </tr>
</table>


```java
@Excel(firstRow = 1,sheet = 0, firstCol = 0)
@Data
public class Task extends BaseExcel
{
    @ExcelColumn(col = 0)
    private String firstName;

    @ExcelColumn(col = 1)
    private String lastName;

    @ExcelColumn(col = 2)
    private Integer age;

    @ExcelColumn(col = 3)
    private Double amount;

    @ExcelColumn(col = 4)
    private String description;
}
```

```java
public class Test
{
    public static void main(String[] args)
    {
        List<Task> taskList = excelReader.read(new File("/some/path/Excel.xlsx"), Task.class);
    }
}
```