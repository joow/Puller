# README

Puller allows you to fetch your comics pull list from [Comixology] and display it.

In order to run Puller you first need to compile and assemble it :

    ./gradlew installDist
    
Then you can run it :

    ./build/install/Puller/bin/Puller -u <username> -p <password>
    
will fetch your latest weekly pull list.

Next time Puller should automatically remember your credentials and the last time you ran it. It will then display your 
pull list from previous date to today. You can override the default behaviour by using different options :

* -f (--from) <from> to change the starting date (the default is either now or the last time Puller was executed)
* -t (--to) <to> to set the ending date (it is set by default to be 6 days from now)
* -u (--username) <username> let you provide your Comixology username
* -p (--password) <password> to specify your Comixology password (you can add a leading space to the whole command if
you don't want that your password appears in your shell history)
* -h (--help) to display the help
* --dry-run if you don't want Puller saving any data (username, password or last ending date)

# TODO

1. Handle Comixology authentication issues by re-trying authentication if failed.
2. Add more tests.
3. Use multiple threads to retrieve pull lists.

[Comixology]: https://pulllist.comixology.com/