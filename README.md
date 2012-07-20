Researchy
==========

A Batman toolbelt for your Scala research code.

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
         *-002.run/ <-- directory for Experiment #1 on yyyy-mm-dd

The experiment directory provides a place to record all output from your
experiment, as well as a place to copy in all information necessary to debug or
recreate your experiment at a later time, such as parameter settings or input
files.

Git Integration
---------------

When you begin an experiment, Researchy records the status of the git
repository: your current branch, and the number of untracked and modified files
you have open.

Researchy will complain (in the log file) if you have any uncommitted or
untracked changes: a good experiment is tied to a reproducible state in the
SCM! But if you insist on being a rebel, you can set Researchy to copy any
un-committed files to the experiment notebook just to be sure you can reproduce
the state of the experiment at a later date.

