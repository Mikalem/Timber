# Timber
 
An app for alumni mentors to help the more recent graduated mentees from
the same university achieve success in their field of study.
 
# About Timber
 
Named “Timber” for representing the similarity to Tinder and the reference to the Acadia Axe-people.
 
As someone who is (almost) a recent graduate, there are many questions on my mind about looking for jobs and what I’m looking for in a job. Through Timber, I’d love to be able to find a mentor who can help answer my questions and perhaps even talk to someone in a different field to see how and why they went about a change in career as they did. Timber is a phone application that strives to link mentors with mentees from the same graduating university in order to help the mentee with the next steps in the recent graduate’s life. It aims to ease the transition from university to work life as well as give some perspective so that the graduate is better prepared. This is more directed to help the mentees, but it offers the opportunity for alumni to give back to their school and remain involved, even if they’re on the other side of the world.
 
Timber will behave like a Tinder clone in how it is operated, however, matches will not be deleted upon swiping left, but they will be moved to the end of the queue. I am choosing to do it this way so that users who don’t know who is out there can make decisions based on what they see in total. Maybe a user will have too high expectations and when they recognize that they’ve gone through the entire queue, they can lower their hopes and select people they may have skipped otherwise.
 
### Similar Applications (and how it’s different)
 
**[Unibly](http://www.unibly.com/#product)**  
A product that allows an admin to pair mentors with mentees for communication. It contains resources to help guide users through the mentoring process and allows for goals to be set with a task list. There are group and individual chats available. There is a newsfeed where users can share articles and prompt discussions.
This product seems to be targeted towards building a local mentoring community that can be manipulated and cared for by an admin or manager.
 
**[IvyExec](https://www.ivyexec.com/)**  
Has a product that links their 20+ mentors and career advisors to help the user with their resume, next steps, online classes, and getting involved in talks and opportunities around them.
 
**[Mentor-Track](https://www.inknowledge.com/mentor-track/)**  
Much like Unibly, but seems targeted more towards students in a classroom setting. Allows students to submit presentations and papers, tracks progress and runs reports. Also anticipates graduation rates and get insight for drop-out rates.
 

**[BluePrint](http://getblueprintapp.com/?page_id=77)**  
Mentees may ask questions related to career paths and mentors can share their wisdom pertaining to these questions.
While it can become a 1-on-1 conversation between a mentor and mentee, it behaves largely like a grand Q&A board where anyone can ask a question and anyone can respond.
 
I’m sure there are many more mentorship applications out there, but these are just a few that caught my eye.
 
### Platform - Android
 
I have chosen to code this application for Android for a couple of reasons. For starters, it is not on IOS, which I have no method to test on (for lack of Apple devices). Second, Android is the original platform tinder was made on, so if I’m to create a clone app, making it on its original platform should end up easier. Ideally, I would develop this app for both IOS and Android so whomever might take over this project would have more leeway and the app itself would be available to more users, but for simplicity sake, it will be strictly android.
 
### Programming Language - Kotlin
 
Android apps support a couple programming languages. Java is one, Android NDK allows building with C and C++, and others. I thought it would be most interesting to use Kotlin. Kotlin is a Java Virtual Machine compatible programming language that has been designed to have more concise syntax than Java, making programming and debugging easier. It is also fully interoperable with Java, which makes the learning process that much more relaxed and will allow for Java code as well.
 
### Database - Firebase
 
It is suggested that Android apps use Firebase as a Database, and since I don’t know any better, Firebase it is.
 
### Features
 
Timber will have many features similar to Tinder as it is a clone, however there will be differences.
 
#### Sign-up/sign-in
When opening the app for the first time, there will be a Sign-up page (with an option to sign-in if an account has previously been made). Unless the user specifically signs-out, the app will open to potential matches after the initial sign-up. If a password has been forgotten, there will be a typical “Forgot your password?” link that’ll allow the user to set up a new one.
 
#### Terms & Agreements and Privacy Policy
The sign-up will require the user to read over and accept the terms and agreements, as well as a privacy policy.
 
#### Loading screen
The loading screen will feature a little axe-person chopping a tree.
A future update could render this to be school specific as this app develops beyond Acadia.
 
#### General Colour Scheme
To represent Acadia, the initial colours will be red and blue. Unless this ends up looking ugly - in which case other colours may be chosen.
A future update could make the colours school specific as Timber develops beyond Acadia.
 
#### Integrated Chat
It would be nice if phone numbers weren’t necessary, but I do understand how it’s mostly used as a identification number to limit accounts made. Phone numbers will only be used for verification and an integrated chat will be developed for the users to interact.
 
#### Potential Matches
Not unlike Tinder, matches are calculated between mentor/mentee and what they’re both “looking for” from their profile and graduating school. Matches are assigned based on graduated school to emphasize common ground and (perhaps) a sense of extended family. Multiple school graduates get matches under both establishments.
Mentors get assigned mentees and vice-versa.
The information displayed is the described later and is slightly different for a mentor or mentee.
 
#### Matching
When there are two positives between a mentor and mentee, the app will bring up a “you have a match!” notification that the user(s) can select to bring up a chat window between both parties. This would be an app notification for the users who aren’t on their smartphone can become aware when they do check their phone.
 
#### Miscellanious
Warnings to the user will be issued when sending a message with email addresses and phone numbers. This is a precaution that can be averted by acknowledging “yes, I want to send this info”
Users can block someone and/or report the person they’ve been matched with in case they become uncomfortable for any reason.
Users can and are encouraged to rate the other party (like in Uber). This helps keeps both parties professional and avoid some app abuse (like users in it to get hired).
 
#### Optional features
An ability to set yourself to “busy” or “not looking for matches”. (Enable/Disable receiving/showing up in potential matches.)
Swiping right produces a little axe animation.
Users are not required to make a decision right away, they can scroll and look at the next one or go back to a previous potential.
Icons can be used rather than words for visual representations - different colours can be used for varied strength in the subject.
If a user changes their “looking for” criteria, previously ‘NO’ed potential matches reset (though some may no longer apply anyway).
When rating, perhaps a written review also available if a more in depth explanation is necessary.
 
### Profile Requirements
 
Required:
- Profession (for mentors only)
- Graduating year
- Degree program
- Geo location
- Who I’m looking for
  - If geo location matters (default: true)
  - If degree matters (default: same degree)
Optional:
- Picture (professional mugshot like in LinkedIn)
- Areas of interest (especially if not directly related to profession/degree)
- (Mentee only) “My burning question” (What they really want answered! (snapshot))
 
### Conclusion
 
While most of the above will surely not be developed at this time, it is well thought-out for the next developer to understand the current vision.
 
The current plan is to implement secure and encrypted login credentials with a log-in, sign-up, and a basic placeholder for Timber itself with a log-out.

#### Credits
Axe in stump icon and scroll icon made by [Freepik](https://www.freepik.com/) from [www.flaticon.com](https://www.flaticon.com/), is licensed by [CC 3.0 BY](http://creativecommons.org/licenses/by/3.0/) (Creative Commons BY 3.0)

Calendar icon made by [Dave Gandy](https://www.flaticon.com/authors/dave-gandy) from [www.flaticon.com](https://www.flaticon.com/), is licensed by [CC 3.0 BY](http://creativecommons.org/licenses/by/3.0/) (Creative Commons BY 3.0)
