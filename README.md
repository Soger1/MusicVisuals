# Music Visualiser Project

Name:Ehan Holohan

Student Number: C20322553

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment

Our goal with this assignment was to make a music video using java code that reacts to the audio of the song

# Instructions

run main located in File c20322553, there are no other special controls the function operates on it's own.

# How it works

it works mostly by using a combination of functions that run temporary effects and a renderlist, which contains objects that are rendered in order, the classes for these shapes are located in the shape.java file. 
A function uses the system clock to create a time variable, we did this as we were having issues where if a computer ran the program slower, it would alter the timing. This time gets past to the draw function, and then the draw function checks which section of the song it is in, then calls different functions based on that section. There are different main functions which are used in the song, there purpose is to at certain intervals create the shape objects, and to call the render function. The functions which create the shapes work by making subclasses of the supertype shape, then adding variables to it based on the type of shape it is, and also different variables like width and height, then they are put into the render list. When the render function is called it takes the render list, and for each shape in it draws it on screen based on its type and parameters. There are certain functions which work outside the render function, such as the sphere and the waveform, this is because they need to be more reactive to the music.

# What I am most proud of in the assignment

We are proud of the renderlist and associated shape objects as it allows for easy rendering of persitant objects. We are also proud of the section which visualises the bubbles, as it is a creative way to have it be audio reactive, while also following the theming of the album.

# Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

