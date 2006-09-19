class Work < ActiveRecord::Base
    
    has_many :human_work_relationships
    has_many :humans, :through => :human_work_relationships
    
    validates_presence_of :title
    
    before_save :check_for_publication
    
    def humansInRelationship(name)
      list = Array.new
      self.human_work_relationships.each do |hwr|
        if hwr.relationship == name
          list.push(hwr.human)
        end
      end
      return list
    end
    
end
