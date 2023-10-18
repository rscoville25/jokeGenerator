// "in the future, humor will be randomly generated"
import scala.swing.*
import scala.util.Random
import java.io.*
import scala.collection.mutable
import scala.collection.mutable.Set
import scala.swing

// setting up the rng
object randomess:
  def rngSet = Random.nextInt(setup1.size)
  def rndPunch: String = punchline(Random.nextInt(punchline.size))
  def rndNoun: String = nouns(Random.nextInt(nouns.size))

// gives maps of punchlines, nouns and setups
val setup1: Map[Int, String] = Map[Int, String](0 -> "Why did the ",
  1 -> "What's the difference between a ",
  2 -> "What do you call a ",
  3 -> "What kind of music do ",
  4 -> "What do you call a rude ")
val setup2: Map[Int, String] = Map[Int, String]( 0 -> " cross the road?",
  1 -> " and a fish?",
  2 -> " with a sore throat?",
  3 -> "s listen to?",
  4 -> "?")
val punchline: Map[Int, String] = Map[Int, String](0 -> "To get to the other side",
  1 -> "A little horse",
  2 -> "You can tune a guitar but you can't tuna fish",
  3 -> "Rock Music",
  4 -> "A frog in a blender",
  5 -> "Jerky")
val nouns: Map[Int, String] = Map[Int, String](0 -> "Chicken",
  1 -> "Fish",
  2 -> "Pony",
  3 -> "Frog",
  4 -> "Guitar",
  5 -> "Muffin",
  6 -> "Car",
  7 -> "Caveman",
  8 -> "Cow"
)

// writing a file for saved jokes
val jokeWriter = new FileWriter(new File("src/jokes.txt"))

// defines the main menu
def mainMenu: Frame =
  new Frame {
    title = "Main Menu"

    contents = new GridPanel(3, 1) {
      contents += new Button("Joke Generator") {
        reactions += {
          case event.ButtonClicked(_) =>
            jokeFrame
            close()
        }
      }

      contents += new Button("What is This?") {
        reactions += {
          case event.ButtonClicked(_) =>
            explanation
            close()
        }
      }

      contents += new Button("Exit") {
        reactions += {
          case event.ButtonClicked(_) =>
            endTxt
            close()

        }
      }
    }

    pack()
    centerOnScreen()
    open()
  }
end mainMenu

// defines the explanation menu
def explanation: Frame =
  new Frame {
    title = "What is this"
    preferredSize = new Dimension(620, 480)

    contents = new GridPanel(2,1) {
      contents += new TextArea() {
        text = "Welcome to the joke generator. " +
          "\nThis program will randomly generate jokes from pools of setups, nouns, and punchlines.\n" +
          "You can also train the program to make better jokes like an AI program," +
          "\nalthough this isn't real AI. There is also a save button that saves the jokes you enjoyed\n " +
          "to a file called jokes.txt"
      }

      contents += new Button("Back to Menu") {
        reactions += {
          case event.ButtonClicked(_) =>
            mainMenu
            close()
        }
      }
    }
    pack()
    centerOnScreen()
    open()
  }

// the frame that generates the jokes
def jokeFrame: Frame =
  var goodJokes = mutable.Set[String]()
  var badJokes = mutable.Set[String]()

  new Frame {
  // set frame title and default frame size
  title = "test"
  preferredSize = new Dimension(620, 480)

  var jokeFinal = ""
  // create a Grid Panel for the buttons
  contents = new GridPanel(4, 4) {
    // top button generates the joke
    contents += new Button("Get Joke") {
      reactions += {
        case event.ButtonClicked(_) =>
          var rnd = randomess.rngSet
          var rndSet1 = setup1(rnd)
          var rndSet2 = setup2(rnd)
          var rndPunch = randomess.rndPunch
          var rndNoun = randomess.rndNoun
          jokeFinal = s"$rndSet1$rndNoun$rndSet2 \n$rndPunch\n"
          while badJokes contains jokeFinal do
            rnd = randomess.rngSet
            rndSet1 = setup1(rnd)
            rndSet2 = setup2(rnd)
            rndPunch = randomess.rndPunch
            jokeFinal = s"$rndSet1$rndNoun$rndSet2 \n$rndPunch\n"

          println(jokeFinal)
          text = jokeFinal
      }
    }

    // a button that designates good jokes
    contents += new Button("Click if this was a good Joke") {
      reactions += {
        case event.ButtonClicked(_) =>
          goodJokes += jokeFinal
          println(goodJokes)
      }
    }

    contents += new Button("Click if this was a bad Joke") {
      reactions += {
        case event.ButtonClicked(_) =>
          badJokes += jokeFinal
          println(badJokes)
      }
    }

    contents += new Button("Save to jokes.txt and exit") {
      reactions += {
        case event.ButtonClicked(_) =>
          val jokeS = goodJokes.toString()
          jokeWriter.write(jokeS)
          jokeWriter.close()
          mainMenu
          close()
      }
    }
  }
  pack()
  centerOnScreen()
  open()
  }
end jokeFrame

def endTxt =
  println("Program ended")

// main def
@main def main(): Unit =
  mainMenu