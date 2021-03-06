# 题目分析

原始题目是：

从某个特定的html文件中提取table里面的具有特定结构的数据，并设计SQL表，把提取出的数据存储入数据库。

对其进行泛化，就是：

从某种来源提取有结构的数据，以存储入SQL数据库。

由此得到以下几个要点：

* 1 对html的解析代码不应该对bar_list.html中table的列名有硬依赖

* 2 SQL表不应该是人肉写的，应该是由代码根据数据源的结构而推断生成的

* 3 html是目前已知的唯一信源，不能排除信源有可能来自于其他地方的可能性

# 初始粗略设计

* extractor，可以从html**或者其他**信源提取原始数据

* formalizer，将原始数据规整化。包括字段名，字段的数据类型，具体字段值的格式

* saver，将规整化后的数据存储到DB

extractor: 信源有各种可能性，不过目前唯一确定的信源来自于html文件，这块先不做扩展设计，可以在后续需求有眉目的时候再做

formalizer: 字段的名字，数据类型，字段值可能五花八门，这块可以先把对未来的扩展机制做了

saver: 这里面临的变化会比较少，应该会是比较稳定的，暂时无需格外过多关注

# how to run

run the ./run_me.sh under the root dir of this project.

then open localhost:8082 to view generated sql data.

Put in these 2 parameters. 

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:./h2DB