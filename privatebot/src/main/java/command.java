import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.interactions.SelectionMenuInteractionImpl;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class command <SlashCommandEvent> extends ListenerAdapter {
    private final List<MessageEmbed> list = new ArrayList<>();

    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String contentRaw = event.getMessage().getContentRaw();
        String[] messageSent = contentRaw.split("\\s+");
        try {

            if (messageSent[0].equalsIgnoreCase("牧野白語錄")) {
                Random randomsum = new Random();

                main.st.executeQuery("SELECT * From quotations limit "+ randomsum.nextInt(312) +",1;");
                ResultSet resultSet = main.st.getResultSet();

                while(resultSet.next()){
                    String text = resultSet.getString(2);
                    String url = resultSet.getString(3);
                    MessageEmbed counterEmbed = this.getMessage(text,url);
                    event.getChannel().sendMessage(counterEmbed).setActionRow(
                            Button.primary("Refresh","刷新語錄"),
                            Button.link("https://www.youtube.com/watch?v=-pLnOMxttz0", "台灣Vtuber-牧野白")
                    ).queue();
                }


            }

        } catch (Exception mue) {
            mue.printStackTrace();
            event.getChannel().sendMessage("白白腦袋卡住了!!").queue();
        }
    }

    private MessageEmbed getMessage(String text,String URL) throws IOException {
        String[] imgURL = {"https://img.league-funny.com/imgur/162553582984_n.jpg","https://i.ytimg.com/vi/-pLnOMxttz0/hqdefault.jpg?sqp=-oaymwEXCOADEI4CSFryq4qpAwkIARUAAIhCGAE=&rs=AOn4CLCJYwzWcofrFWPA_eJEEUNIfzODDg","https://pgw.udn.com.tw/gw/photo.php?u=https://uc.udn.com.tw/photo/2021/07/07/1/12903556.png&s=Y&x=0&y=0&sw=1250&sh=703&sl=W&fw=800"};
        Random randomimg = new Random();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("牧野白語錄", null);
        eb.setColor(Color.ORANGE);
        eb.addField("語錄: "+text, "出處: "+URL, false);
        eb.setImage(imgURL[randomimg.nextInt(3)]);
        return eb.build();
    }

    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("Refresh")) {
            try {
            Random randomsum = new Random();

            main.st.executeQuery("SELECT * From quotations limit "+ randomsum.nextInt(312) +",1;");
            ResultSet resultSet = main.st.getResultSet();

            while(resultSet.next()){
                String text = resultSet.getString(2);
                String url = resultSet.getString(3);
                MessageEmbed counterEmbed = this.getMessage(text,url);
                event.editMessageEmbeds(counterEmbed).setActionRow(
                        Button.primary("Refresh","刷新語錄"),
                        Button.link("https://www.youtube.com/watch?v=-pLnOMxttz0", "台灣Vtuber-牧野白")
                ).queue();
            }
            } catch (Exception mue) {
                mue.printStackTrace();
                event.getChannel().sendMessage("白白腦袋卡住了!!").queue();
            }

            }
        }
    }
