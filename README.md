# Package-delivery

Application for Package delivery

## Main menu
| Option | Describe |
| ------ | ------ |
| 1 | create delivery package, input format {postalCode = fixed 5 digits, weight = positive number, maximal 3 decimal places} exmaple: 02354 14.321 |
|   | if you insert bad format of input fields the package is not saved |
| 2 | insert input file contains packages, format of input file is .txt and format of packages is describe in choice one |
|   | If is invalid input program show error message |
| 3 | insert input file contains package fees, format of input file is .txt and format of package fee is { weigth = positive number maximal 3 decimal places |
|   | fee = maximal 2 decimal places} example: 10 5.00 |
|   | If you insert bad format of input fileld the package fee is not saved |
|   | If an invalid input is inserted, the app prints an error message |
| 4 | quit the program |

## Build
```sh
$ mvnw clean install
$ cd {project-path}/tartget/
$ java -jar packagedelivery-0.0.1-SNAPSHOT.jar
```

## Notes
After one minute the inserted package will be displayed

If a fee has been entered, they will be added to the packages
