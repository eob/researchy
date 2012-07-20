name := "researchy"

organization := "eob"

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "repo.codahale.com" at "http://repo.codahale.com"

libraryDependencies += "com.codahale" %% "logula" % "2.1.3"

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit.pgm" % "1.1.0.201109151100-r"

maxErrors := 20
