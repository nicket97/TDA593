# TDA593

## Open with IntelliJ
It's easier to not clone via IntelliJ's version control. Clone the project with the command line. In IntelliJ, choose `File > Open`, and pick the folder **TDA593/ROVU/Core/** to open. This is the easiest solution, since IntelliJ messes it up if you open up the enclosing folder, it won't be able to locate the `src`-folder. 

Set your Project SDK to version **1.8** in `File > Project Structure...`.

## Git Workflow
1. Stand on the branch that you want to work on. `git checkout <branchname>`
2. Create your own local branch `git checkout -b <local_branchname>`
3. Add your code, add the changes, and commit the changes.
    - `git add -A` to add all the files you've changed and added.
    - `git commit -m "<Your commit message>"` to commit the changes. It's good to use imperative language. Think like "With this commit applied, it will... <Your commit message>". For example: with this commit applied, it will **update the model**. 
4. Stand on the branch that you wanted to work on (from step 1). `git checkout <branchname>`
5. Pull down the latest changes from the cloud (if there have been other people working on the same branch). `git pull`.
6. Move to your own local branch `git checkout <local_branchname>`
7. Put your changes on top of the changes others have made by rebasing: `git rebase <branchname>`
8. Move to the branch you wanted to work on (from step 1). `git checkout <branchname>`
9. Merge the branch with your local branch `git merge <local_branchname>`
10. Push your changes: `git push`

And voila! It should work. If you are unsure about anything, don't hesitate to ask! :) 