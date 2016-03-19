# APCC
Educational application for Android.


This project has the objective to develop an educational platform targeted at children, with ages from 6 to 10, with physical disabilities, mainly cerebral palsy.

To do that this application takes advantage of "swipe" algorithm where automatically, and sequentially, the interactive elements are focused and can be selected with a single touch on the screen or resorting to an external device, for example a "switch".

By providing swipe compatibility and multiple types of exercises, all configurable, it aims to provide educators and families the tools needed to better interact and teach with those children.

Currently the application has currently the client module, intended for the children to use.

Later in the development it will be created a module to allow the setting of all the existent exercises and a server to allow the comunication between the other two modules.


Description:
    A prototype of an educational app with the children, from age 6-10 in mind,
    suffering of cerebral palsy.
    This prototype allows the professor, or person in charge of the child, to
    set up activities, composed of lessons and exercises,
    using the existent mechanics.
    By taking advantage of a "swipe" mechanism, where interactive items
    are sequentially focused, it allows interaction using only
    one button/switch/touch.

    The configuration of the aforementioned activities is done by setting up
    an hierarchy of structured directories and JSON files with the
    relevant information, and resource/media files.

Note:
    The given setup exercises, in a separate APCC folder, are in Portuguese
    as the target market at the point of development were the children of
    a Portuguese institution.

#######################
The hierarchy of the directories/JSON is as follows:

"APCC" -> root directory

    "metadata.json" -> contains the information to be parsed by the app about
            the directory structure and existing activities.

    "Categories"

        Sub-Category 1 -> Category 1 of a given set of activities

            Level 1

            Level 2 -> Difficulty level of the activities contained

            Level 3

        Sub-Category 2

            Level 1

                Activity 1 -> Activity composed of lessons/exercises/resources

                Activity 2

                    "Headers" -> Header of this activity (title and other info)

                    "Exercises" -> Existing JSON files
                            with the exercise information.

                    "Lessons" -> Existing JSON files
                            with the exercise information go here.

                    "Resources" -> Resource files (images) go to this directory.

NOTE:
    - Aside the names marked with quotes, they can be whatever the user wants as
    long as they are correctly labeled and organized on the "metadata.json".

####################
Installation:
    - Drop the APK file into your android device and install the application.

    - Unzip the APCC folder, that contains some preset activities,
        and put it on the external storage of your Android device.

    - The application should now open without problems.

####################
Instructions:
    - Title bar
        - The Swipe mechanism can be turned off/on with the button
            indicating the "ON"/"OFF" button.

        - The '+'/'-' buttons adjust the swipe speed.

    - Footer bar
        - Has a back button, "Voltar", and next button, "Seguinte",
            used to return to the last used and next screen in the application,
            respectively.

    - Header
        - Presents the activity information, for example name and sub-category.

    - Body
        - Will change according to the flow of actions.

        - It is used to present the
            "Activity Selection Screen"
                Used to select the category and theme of the lesson.
            "Lessons"
                Lesson according to the chosen theme.
            "Exercises"
                Exercises also subjected to the chosen theme.

        - Each screen will present it's own set of buttons and respective actions.
            "Activity Selection Screen" has the following flow:
                Category -> Difficulty Level -> Theme
            "Lessons"
                A sequence of image/text screens to teach the user about the
                chosen theme.
            "Exercises"
                Different exercises based on multiple choice and variants.

####################
Known Bugs:
    - The "Activity Selection Screen" can only be traversed using
        the swipe feature. Currently the item list isn't able to process the
        touch selection signal.
        Current workaround:
            Use the swipe feature by leaving/turning it "ON", at the Title bar,
            and touching on the screen aside the list items or other
            input device connected to the Android device.

    - The back button currently doesn't let the user to leave the application
        on the first screen.
