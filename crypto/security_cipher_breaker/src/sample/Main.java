package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main{

    // Use text in arg[0] to derive the key and then decrypt text in arg[1] with it
    public static void main(String[] args) {

        if(args[0] == null || args[0].isEmpty() || !(new File(args[0])).exists()){
            System.out.println("Error cipherpath was not specified or does not exists - arg[0] : " + args[0]);
            return;
        }
        if(args[0] == null || args[0].isEmpty() || !(new File(args[0])).exists()){
            System.out.println("Error secretPath was not specified or does not exists - arg[1] : " + args[1]);
            return;
        }

        String cipherPath = args[0];
        System.out.println("Cipherpath:\n");
        System.out.println(cipherPath);
        String secretPath = args[1];
        System.out.println("Secretpath:\n");
        System.out.println(secretPath);

        System.out.println("\n");

        CipherManager manager = new CipherManager();
        String plain = manager.forceDecryptFile(cipherPath);
        System.out.println("Extracted plaintext from " + cipherPath);
        System.out.println(plain);

        //Relevant Code starts here
        System.out.println("\n");
        System.out.println("Extracting key from " + cipherPath);
        //Perform frequency analysis on cipher ann get key
        String key = manager.forceDecryptFileGetKey(cipherPath);
        System.out.println("Extracted Key: " + key);
        System.out.println("Decrypting " + secretPath + " with " + key);
        //Decrypt secret text with extracted Key
        String secret = manager.DecryptFromPath(secretPath,key);
        // Print the secret to console
        System.out.println("Decrypted Secret: " + secret);
    }

    public void test(){
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();

        /*String testKey = "]BCDKFGHIJELMNOPQRSTUVWXYZ0123456789.,!?()[A";
        String testPlain = "The ultimate guide to becoming an entrepreneur\n" +
                "3.3k shares Share on Facebook Share on Twitter\n" +
                "\n" +
                "You don’t need qualifications, money, a planet-sized-brain or even a particularly good idea. All an entrepreneur ever does is create something that consistently makes money.\n" +
                "\n" +
                "Think of a company as a machine you design and build. Here’s McDonalds:\n" +
                "\n" +
                "The ultimate guide to being an entrepreneur\n" +
                "\n" +
                "Your ‘machine’ always has certain parts. It sells something to someone, and re-invests some of that to help make more sales in future. What’s left over is profit for the owners. Here’s Google:\n" +
                "\n" +
                "Google's business machine\n" +
                "\n" +
                "If you can design, build, own and care for such a machine, you can become very rich indeed. That doesn’t mean it’s easy, but most of the barriers that you think will stop you won’t. Interested?\n" +
                "\n" +
                "Let’s talk about you\n" +
                "Are you young, poor, unqualified – a student, or hating your job? Maybe a touch rebellious? Perfect. You have no bad habits, and will work until your fingernails fall out and your eyeballs roll onto the desk. The world awaits you.\n" +
                "\n" +
                "Older, wiser, bit of money saved, experienced with a stable job? Maybe a mortgage and kids? Your job is much harder. It can be done, but it might feel like you’re trying to dance backwards through quicksand.\n" +
                "\n" +
                "The most important qualities of a good entrepreneur are energy and determination. It doesn’t hurt to be persuasive, but this can be learned. I started as a shy uber-nerd aged 21; I soon learned how to sell when it was the only way to feed myself.\n" +
                "\n" +
                "Enough preamble. Let’s make you a bajillion dollars:\n" +
                "\n" +
                "The idea\n" +
                "Please forget all of the terrible deluded nonsense you’ve heard about the value of ideas. Ideas are cheap, fleeting things; by itself an idea is worth less than a half-eaten sandwich. At least you can eat the sandwich.\n" +
                "\n" +
                "You do need an idea of course. But understand that even the most successful companies were not founded on wild or brilliant ideas. Starbucks chose the brazen path of selling coffee in Seattle. Facebook built a better MySpace. Google built a better Yahoo search. Microsoft copied Apple – who copied Xerox.\n" +
                "\n" +
                "Original ideas are overrated. What isn’t overrated is timing. Google chose the perfect time to build a better search engine – good luck trying to do that now *cough* Bing *cough*. What you want, therefore, is an astute awareness of a need that is currently underrepresented in the market. You want to spot a product or service that can go places – original or not. It’s usually easier to refine an existing idea that isn’t fully realised than to create a wholly original one.\n" +
                "\n" +
                "People fear setting up a business wherever there’s competition, but competition can be a good thing. The best place to set up a new restaurant is right next to another successful restaurant; they’ve kindly done the hard work for you of building an audience. Many a good business has ridden to success on the coattails of another – it is usually better to have some rivals over none. You just need to become 10% better.\n" +
                "\n" +
                "I personally recommend trying to deliver something that you and your friends would buy in a heartbeat. You’ll know more about your field, you’ll understand your customers, and you’ll be passionate about what you do. If you can make your company about a why – not a what – you’ll inspire yourself and those around you. And to survive the next step, you need a fair sprinkle of inspiration:\n" +
                "\n" +
                "Starting\n" +
                "Starting a company is a bit like parenting; everyone assumes you know what you’re doing, but babies and companies don’t come with instruction manuals. You stumble through it, learning as you go.\n" +
                "\n" +
                "It’s at the start where you’re most likely to fail. Your aim is to build that magical money-making machine, but you probably don’t have all the parts and the ones that you need may cost more than you have. Your idea is probably at least half wrong too, but you won’t know which half yet. All of this is normal.\n" +
                "\n" +
                "All of this is normal\n" +
                "\n" +
                "A big part of starting a company is convincing people to believe in you before they probably should. When Steve Jobs founded Apple, he had no money and no customers; what he did next is the hallmark of a great entrepreneur. First he convinced a local computer store to order his non-existent Apple computers, with payment on delivery. He then convinced a parts supplier to sell him the components he needed to build them – using the order he just obtained as proof he would be able to pay them back. Jobs and a small team worked in their garage to build the first computers, delivered them on time and made a tidy profit. Apple was born from nothing.\n" +
                "\n" +
                "Apple 1 Computer\n" +
                "\n" +
                "Most new entrepreneurs play a few gambits early on like this. If it sounds scary, that’s because it is. I once had to pay staff salaries on my heavily burdened credit cards when an early order fell through. You fake it until you make it.\n" +
                "\n" +
                "While doing all this you need to juggle between making the perfect company (idealist) and paying your bills (realist) – an absence of either will eventually kill you. I believe it’s one reason why realist / idealist partnerships are so common in business.\n" +
                "\n" +
                "Do not scale prematurely. Don’t try to be a big company early on – just aim to be one. Be slow to spend and to hire at first. Don’t waste time writing mission statements and policy documents. You’re small, nimble and on a mission. Make and sell things. There’ll be time for a HR department later.\n" +
                "\n" +
                "Don’t be surprised if you change your company entirely. It’s a rare business that survives first contact with its customers. Try to avoid doing this more than once though, it doesn’t pay well.\n" +
                "\n" +
                "Survive long enough, reinvest your meagre successes and compound them. Eventually, you can move on to:\n" +
                "\n" +
                "Extracting yourself\n" +
                "This is the step most small businesses never accomplish.\n" +
                "\n" +
                "Up until now, your magical business machine almost certainly contains one irreplaceable part: you. If your background is accounts, you’re probably the head accountant. If you’re a programmer, you’re probably the best coder. Whatever you do, chances are you’ll feel essential and somewhat overworked.\n" +
                "\n" +
                "This is not a business. This is you self employed as your own slave.\n" +
                "\n" +
                "Here’s the hard part: you need to make yourself redundant. If you dropped dead tomorrow, your business should carry on working just fine. All of your time needs to be spent working on your business, not for your business. The alternative is you’re basically self-employed with assistants.\n" +
                "\n" +
                "Some businesses can’t escape this trap. If you’re a brilliant copywriter – say – you’ll struggle. It’s because what makes you a great company is you, and unless you can bottle up you into a business model, you can’t grow.\n" +
                "\n" +
                "McDonalds built a business that works even if they hire almost entirely minimum wage workers. Their process makes it work: every burger is efficient and nearly indistinct, and nothing is left to chance. Their brand is so strong people line up worldwide to eat there. Your business may be radically different, but it should be similarly robust.\n" +
                "\n" +
                "If you accomplish this, you now own something that is self-sustaining. You should be able to pull a good salary even if you never go into work. Your time is now free to tweak your business endlessly into something better. Now to conquer the world, all you need to do is:\n" +
                "\n" +
                "Scale\n" +
                "The final step is a bit like playing Who Wants to Be A Millionaire. Each question you get right doubles your money, or you’re going home.\n" +
                "\n" +
                "Who wants to be a billionaire?\n" +
                "\n" +
                "Do not make the naive mistake of assuming a big company is like a small one but bigger. Oh, nevermind. That’s like telling your kids to listen to you, really, drinking doesn’t make you cool. You’ll learn the hard way.\n" +
                "\n" +
                "As a company grows the rules and your culture change completely. You may even find yourself disliking the company you created (many founders feel conflicted like this, eventually). If you’ve made it this far, you have many options: hire help, sell, or double-down and see where the ride takes you.\n" +
                "\n" +
                "Remember no business can grow indefinitely. Most industries are more efficient at different sizes – it’s easy to be a two-man plumbing company, but near impossible to build a 1,000 man plumbing corporation. Know the limits of yours well in advance. Software is an example of an industry that scales exceedingly well, which is why it creates so many young billionaires.\n" +
                "\n" +
                "And finally\n" +
                "It’s never been easier to start a company. You can create a killer product in your student dorm without even registering any paperwork – that was enough for Facebook.\n" +
                "\n" +
                "I think entrepreneurship is a form of enlightened gambling. Skill and tenacity are big factors, but luck plays a big part. However, as long as you can keep picking yourself up when you get knocked down, try different things and keep learning, the odds are in your favour. You just have to dare to chance them.\n" +
                "\n" +
                "Enjoyed this? Get future posts emailed to you.\n" +
                "\n" +
                " \n" +
                "Keep reading\n" +
                " The problem isn’t that life is unfair - it’s your broken idea of fairness\n" +
                "The problem isn’t that life is unfair - it’s your broken idea of fairness\n" +
                "258k shares\n" +
                "\n" +
                " How to succeed when you have no special skills\n" +
                "How to succeed when you have no special skills\n" +
                "2.7k shares\n" +
                "\n" +
                " The secret to winning is quitting\n" +
                "The secret to winning is quitting\n" +
                "935 shares\n" +
                "\n" +
                "Comments\n" +
                "Comment rules: Critical is fine, but if you’re trolling, I'll delete your stuff. Have fun and thanks for adding to the conversation.\n" +
                "\n";

        String testCipher = manager.Encrypt(testPlain,testKey,false);
        System.out.println("TestCipher\n" + testCipher);
        char eReplacementChar = manager.FrequencyAnalyseForE(testCipher,0);
        System.out.println("ECHAR: " + eReplacementChar);
        String bins[] = manager.createBinsFromText(manager.filterPlainText(testPlain));
        for(int i = 0; i< bins.length; i++){
            //System.out.println("Statistics for Bin: " + i);
            //manager.PrintStatistic(bins[i]);
            //manager.PrintBiggestLetter(bins[i]);
        }


        String testReconstKey = manager.deriveKeyFromCipher(testCipher);
        System.out.println("Real Key: \n" + testKey);
        System.out.println("Reconstructed Key: \n" + testReconstKey);
        String reconstPlain = manager.Decrypt(testCipher,testReconstKey);
        System.out.println("Reconstructed Plain: \n" + reconstPlain);
        System.out.println("Original Plain: \n" + manager.filterPlainText(testPlain));*/

        //System.out.println("\n\n");

        //System.out.println((new File("..\\..\\..\\")).getAbsolutePath().toString());
    }
}
