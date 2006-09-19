class Book < Work
  
  # If the book has had its published flag set to true, checks
  # that it fulfills the rules for being published:
  # - defines an author or an editor, that author maybe "Unknown"
  # - defines a publisher
  # - defines a from_year field (= year of publication)
  # - if to_year is defined, it must be *after* from_year
  def check_for_publication
    puts "implement publication check"
  end
end
