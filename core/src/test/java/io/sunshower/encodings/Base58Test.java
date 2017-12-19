package io.sunshower.encodings;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
public class Base58Test {


    @Test
    public void ensureEncodingStringWithDefaultEncodingWorksForSimpleString() {

        String input = "hello";

        Encoding instance = Base58.getInstance(
                Base58.Alphabets.Default
        );
        String result = instance.encode(input.getBytes());
        String expected = "Cn8eVZg";
        assertThat(result, is(expected));
    }


    @Test
    public void ensureDecodingWorksForSimpleString() {
        Encoding instance = Base58.getInstance(
                Base58.Alphabets.Default
        );

        String input = "Cn8eVZg";

        assertThat(new String(instance.decode(input)), is("hello"));
    }

    @Test
    public void ensureRandomUUIDsAllDecodeAndEncodeCorrectly() {
        Encoding instance = Base58.getInstance(
                Base58.Alphabets.Ripple
        );
        for(int i = 0; i < 100; i++) {
            UUID id = UUID.randomUUID();
            String input = id.toString();
            String encoded = instance.encode(input);
            assertThat(new String(instance.decode(encoded)), is(input));
        }

    }


    @Test
    public void ensureIdsStringIsEncodedAndDecodedCorrectly() {
        String result = "11W6rgS6AQZMn5FKaK59";

        Encoding instance = Base58.getInstance(Base58.Alphabets.Default);

        byte[] decode = instance.decode(result);
        assertThat(instance.encode(decode), is(result));


    }
    
    @Test
    public void ensureEncodingIsIdempotent() {
        Encoding instance = Base58.getInstance(
                Base58.Alphabets.Default);
        
        String value = "k5UyzPLWuTANuE9a1xdx6W";
        assertThat(instance.encode(instance.decode(value)), is(value));
    }

    @Test
    public void ensureCommonPunctuationCharactersAreEncoded() {

        String input = "!@#$%^&*()_+";

        Encoding instance = Base58.getInstance(
                Base58.Alphabets.Default);
        String result = instance.encode(input.getBytes());

        assertThat(result, is("dPoCNNENvGW2ngdG"));
    }

}