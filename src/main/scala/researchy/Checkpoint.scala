// Copyright 2012 Edward Benson (eob@csail.mit.edu)

package researchy;

import org.eclipse.jgit._
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib.{Repository, RepositoryBuilder, ObjectId}
import java.io.File
import com.codahale.logula.Logging

/**
 * Singleton to provide git integration.
 */
object Checkpoint extends Logging {

  def gitRepository : Repository = {
    val builder = new RepositoryBuilder()
    val repo = builder.setGitDir(new File("./.git")).readEnvironment().findGitDir().build()
    if (repo != null) {
      return repo
    } else {
      return null
    }
  }

  def isCheckedIn : Boolean = {
    val repo = Checkpoint.gitRepository
    if (repo != null) {
      val git = new Git(repo)
      val status = git.status().call()
      val modified = status.getModified().size()
      val untracked = status.getUntracked().size()
      log.info("GIT: %d uncommitted, modified files", modified)
      log.info("GIT: %d uncommitted, untracked files", untracked)
      
      if ((modified > 0) || (untracked > 0)) {
        log.info("GIT: Running with uncommitted changes.")
        return false
      } else {
        log.info("GIT: Everything checked in!")
        true
      }
    } else {
      log.info("GIT: null repository object returned. Are you using git?")
      return false
    }
  }

  def recordCheckpoint {
    val repo = Checkpoint.gitRepository
    if (repo != null) {
      val git = new Git(repo)
      val status = git.status().call()
      val modified = status.getModified().size()
      val untracked = status.getUntracked().size()
      val head = repo.resolve("HEAD"); 

      Dump.line("HEAD: %s".format(head), "managed/git.txt")
      Dump.line("modified files: %d".format(modified), "managed/git.txt")
      Dump.line("untracked files: %d".format(untracked), "managed/git.txt")
    } else {
      Dump.line("No git repository found. Are you using git?", "managed/git.txt")
    }
  }
}
