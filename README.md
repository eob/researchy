Researchy
==========

Researchy is a Batman utility belt for your Scala research code.

Experiment Notebook to store Provenance, Output, and Logs
---------------------------------------------------------

Ensure that no data is lost when you perform an experiment. Just tell Researchy
to begin an experiment at the top of your `main` function:

    Experiment.begin(args, "Experiment Name")

and the `Experiment` singleton creates a new folder for this experiment for all
data to be stored.

    PROJECT-ROOT
    |
    *- src/
    |
    *- lib/
    |
    *- experiments/
       |
       *-YYYY-MM-DD/
         |
         *-001.run/ <-- directory for Experiment #1 on yyyy-mm-dd
         |
         *-002.run/ <-- directory for Experiment #2 on yyyy-mm-dd

The experiment directory provides a place to record all output from your
experiment, as well as a place to copy in all information necessary to debug or
recreate your experiment at a later time, such as parameter settings or input
files.

Git Integration
---------------

When you begin an experiment, Researchy records the status of the git
repository in your experiment notebook. This includes current branch and the
number of untracked and modified files you have open.

Researchy will complain (in the log file) if you have any uncommitted or
untracked changes: a good experiment is tied to a reproducible state in the
SCM! But if you insist on being a rebel, you can set Researchy to copy any
un-committed files to the experiment notebook just to be sure you can reproduce
the state of the experiment at a later date.

Managed Timers and Data Dumping
-------------------------------

The `Timers` and `Dump` objects provide one-liners that enable you to time
events and dump various shapes of data to your Experiment Notebook. Use these
instead of (or in addition to) normal logging and Researchy will provide you
with nice, structured data to analize and archive.

Multi-File Thrift IO
--------------------

It's often convenient to split data across multiple files. Researchy provides
Thrift object read/write streams across sets of files in addition to individual
files.

Evaluation Frameworks
---------------------

Researchy provides standardized facilities for computing metrics such as
f-score, precision, and recall. Just provide the data and the evaluation
function, and you don't have to worry about writing an evaluation harness
yourself.

